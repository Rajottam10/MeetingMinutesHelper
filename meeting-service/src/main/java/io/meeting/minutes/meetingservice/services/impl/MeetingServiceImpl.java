package io.meeting.minutes.meetingservice.services.impl;

import io.meeting.minutes.meetingservice.constants.AttendanceStatus;
import io.meeting.minutes.meetingservice.constants.MeetingStatus;
import io.meeting.minutes.meetingservice.constants.MeetingType;
import io.meeting.minutes.meetingservice.dtos.MeetingRequestDto;
import io.meeting.minutes.meetingservice.dtos.MeetingResponseDto;
import io.meeting.minutes.meetingservice.entities.AgendaItem;
import io.meeting.minutes.meetingservice.entities.Meeting;
import io.meeting.minutes.meetingservice.entities.MeetingParticipant;
import io.meeting.minutes.meetingservice.entities.MeetingSeries;
import io.meeting.minutes.meetingservice.entities.User;
import io.meeting.minutes.meetingservice.exceptions.InvalidRequestException;
import io.meeting.minutes.meetingservice.exceptions.ResourceNotFoundException;
import io.meeting.minutes.meetingservice.mapper.MeetingMapper;
import io.meeting.minutes.meetingservice.repositories.MeetingParticipantRepository;
import io.meeting.minutes.meetingservice.repositories.MeetingRepository;
import io.meeting.minutes.meetingservice.repositories.MeetingSeriesRepository;
import io.meeting.minutes.meetingservice.repositories.UserRepository;
import io.meeting.minutes.meetingservice.services.MeetingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingSeriesRepository meetingSeriesRepository;
    private final MeetingParticipantRepository meetingParticipantRepository;
    private final UserRepository userRepository;
    private final MeetingMapper meetingMapper;

    public MeetingServiceImpl(MeetingRepository meetingRepository,
                              MeetingSeriesRepository meetingSeriesRepository,
                              MeetingParticipantRepository meetingParticipantRepository,
                              UserRepository userRepository,
                              MeetingMapper meetingMapper) {
        this.meetingRepository = meetingRepository;
        this.meetingSeriesRepository = meetingSeriesRepository;
        this.meetingParticipantRepository = meetingParticipantRepository;
        this.userRepository = userRepository;
        this.meetingMapper = meetingMapper;
    }

    @Override
    @Transactional
    public MeetingResponseDto createMeeting(MeetingRequestDto dto) {
        validateRequest(dto);

        Meeting meeting = meetingMapper.convertToMeeting(dto);
        meeting.setMeetingOrganizer(findUserByEmail(dto.getMeetingOrganizer(), "organizer"));
        if (StringUtils.hasText(dto.getMeetingChair())) {
            meeting.setMeetingChair(findUserByEmail(dto.getMeetingChair(), "chair"));
        }
        if (dto.getMeetingSeriesId() != null) {
            MeetingSeries series = meetingSeriesRepository.findById(dto.getMeetingSeriesId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "The meeting series with the id " + dto.getMeetingSeriesId() + " was not found."));
            meeting.setMeetingSeries(series);
        }

        addParticipants(meeting, dto.getMeetingParticipants(), AttendanceStatus.INVITED);
        addParticipants(meeting, dto.getDistributionRecipients(), AttendanceStatus.RECIPIENT_ONLY);
        addAgendaItems(meeting, dto.getAgendaItems());

        Meeting savedMeeting = meetingRepository.save(meeting);
        return meetingMapper.convertToMeetingResponseDto(savedMeeting);
    }

    // Rules that need no database lookup. Title is deliberately NOT checked for
    // uniqueness: recurring meetings share a title and must stay separate records.
    private void validateRequest(MeetingRequestDto dto) {
        if (dto.getMeetingType() == MeetingType.ONLINE && !StringUtils.hasText(dto.getMeetingLink())) {
            throw new InvalidRequestException("An online meeting needs a meeting link.");
        }
        if (dto.getMeetingType() == MeetingType.PHYSICAL && !StringUtils.hasText(dto.getMeetingLocation())) {
            throw new InvalidRequestException("A physical meeting needs a meeting location.");
        }
        if (dto.getMeetingParticipants() != null && dto.getDistributionRecipients() != null) {
            Set<Long> overlap = new HashSet<>(dto.getMeetingParticipants());
            overlap.retainAll(dto.getDistributionRecipients());
            if (!overlap.isEmpty()) {
                throw new InvalidRequestException(
                        "A user cannot be both a participant and a distribution recipient: " + overlap);
            }
        }
    }

    private User findUserByEmail(String email, String roleInMeeting) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No user was found for the " + roleInMeeting + " email."));
    }

    private List<User> loadUsers(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return List.of();
        }
        Set<Long> distinctIds = new LinkedHashSet<>(userIds);
        List<User> users = userRepository.findAllById(distinctIds);
        if (users.size() != distinctIds.size()) {
            Set<Long> foundIds = users.stream().map(User::getId).collect(Collectors.toSet());
            List<Long> missing = distinctIds.stream().filter(id -> !foundIds.contains(id)).toList();
            throw new ResourceNotFoundException("No users found for ids: " + missing);
        }
        return users;
    }

    private void addParticipants(Meeting meeting, List<Long> userIds, AttendanceStatus status) {
        for (User user : loadUsers(userIds)) {
            MeetingParticipant participant = new MeetingParticipant();
            participant.setMeeting(meeting);
            participant.setUser(user);
            participant.setAttendanceStatus(status);
            meeting.getMeetingParticipants().add(participant);
        }
    }

    private void addAgendaItems(Meeting meeting, List<String> agendaItemNames) {
        if (agendaItemNames == null) {
            return;
        }
        for (String name : agendaItemNames) {
            if (StringUtils.hasText(name)) {
                AgendaItem item = new AgendaItem();
                item.setName(name.trim());
                item.setMeeting(meeting);
                meeting.getAgendaItems().add(item);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeetingResponseDto> getAllMeetings() {
        return meetingRepository.findAll().stream()
                .map(meetingMapper::convertToMeetingResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MeetingResponseDto findMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "The meeting with the id " + id + " was not found."));
        return meetingMapper.convertToMeetingResponseDto(meeting);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MeetingResponseDto> searchMeetings(String title, LocalDateTime from, LocalDateTime to,
                                                   Long seriesId, Long participantId, MeetingStatus status) {
        String titlePattern = StringUtils.hasText(title) ? "%" + title.trim().toLowerCase() + "%" : null;
        return meetingRepository.search(titlePattern, from, to, seriesId, status, participantId).stream()
                .map(meetingMapper::convertToMeetingResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public MeetingResponseDto updateAttendance(Long meetingId, Long userId, AttendanceStatus attendanceStatus) {
        MeetingParticipant participant = meetingParticipantRepository
                .findByMeetingIdAndUserId(meetingId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User " + userId + " is not on the participant list of meeting " + meetingId + "."));
        participant.setAttendanceStatus(attendanceStatus);
        return meetingMapper.convertToMeetingResponseDto(participant.getMeeting());
    }
}