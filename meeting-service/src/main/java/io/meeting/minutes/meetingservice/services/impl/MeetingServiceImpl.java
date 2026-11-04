package io.meeting.minutes.meetingservice.services.impl;

import io.meeting.minutes.meetingservice.constants.AttendanceStatus;
import io.meeting.minutes.meetingservice.constants.MeetingType;
import io.meeting.minutes.meetingservice.dtos.MeetingRequestDto;
import io.meeting.minutes.meetingservice.dtos.MeetingResponseDto;
import io.meeting.minutes.meetingservice.entities.Meeting;
import io.meeting.minutes.meetingservice.entities.MeetingParticipant;
import io.meeting.minutes.meetingservice.entities.User;
import io.meeting.minutes.meetingservice.mapper.MeetingMapper;
import io.meeting.minutes.meetingservice.repositories.MeetingRepository;
import io.meeting.minutes.meetingservice.repositories.UserRepository;
import io.meeting.minutes.meetingservice.services.MeetingService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;
    private final UserRepository userRepository;

    private MeetingServiceImpl(MeetingRepository meetingRepository, MeetingMapper meetingMapper, UserRepository userRepository){
        this.meetingRepository = meetingRepository;
        this.meetingMapper = meetingMapper;
        this.userRepository = userRepository;
    }

    @Override
    public MeetingResponseDto createMeeting(MeetingRequestDto meetingRequestDto) {
        validateRequest(meetingRequestDto);
        Meeting meeting = meetingMapper.convertToMeeting(meetingRequestDto);
        meeting.setMeetingOrganizer(findUserByEmail(meetingRequestDto.getMeetingOrganizer(), "Organizer"));
        if (StringUtils.hasText(meetingRequestDto.getMeetingChair())) {
            meeting.setMeetingChair(findUserByEmail(meetingRequestDto.getMeetingChair(), "Chair"));
        }
        for (User user : loadParticipants(meetingRequestDto.getMeetingParticipants())) {
            MeetingParticipant participant = new MeetingParticipant();
            participant.setMeeting(meeting);
            participant.setUser(user);
            participant.setAttendanceStatus(AttendanceStatus.INVITED);
            meeting.getMeetingParticipants().add(participant);
        }
        meeting.setStartDateTime(LocalDateTime.now());
        Meeting savedMeeting = meetingRepository.save(meeting);
        return meetingMapper.convertToMeetingResponseDto(savedMeeting);
    }

    private void validateRequest(MeetingRequestDto meetingRequestDto){
        meetingRepository.findMeetingsByTitle(meetingRequestDto.getTitle()).ifPresent(existingMeeting -> {
            throw new RuntimeException("Meeting "+ meetingRequestDto.getTitle()+" already exists.");
        });
        if (meetingRequestDto.getMeetingType() == MeetingType.ONLINE && !StringUtils.hasText(meetingRequestDto.getMeetingLink())) {
                throw new RuntimeException("An online meeting needs a meeting link.");
        }
        if (meetingRequestDto.getMeetingType() == MeetingType.PHYSICAL && !StringUtils.hasText(meetingRequestDto.getMeetingLocation())) {
                throw new RuntimeException("A physical meeting needs a meeting location.");
        }
    }

    private User findUserByEmail(String email, String roleInMeeting){
        return userRepository.findByEmail(email).orElseThrow( ()-> new RuntimeException("No participant for the role "+roleInMeeting+ " was found."));
    }

    private List<User> loadParticipants(List<Long> participantIds) {
        if (participantIds == null || participantIds.isEmpty()) {
            return List.of();
        }
        Set<Long> distinctIds = new LinkedHashSet<>(participantIds);
        List<User> users = userRepository.findAllById(distinctIds);
        if (users.size() != distinctIds.size()) {
            Set<Long> foundIds = users.stream().map(User::getId).collect(Collectors.toSet());
            List<Long> missing = distinctIds.stream().filter(id -> !foundIds.contains(id)).toList();
            throw new RuntimeException("No users found for participant ids: " + missing);
        }
        return users;
    }

    @Override
    public List<MeetingResponseDto> getAllMeetings() {
        List<Meeting> meetings = meetingRepository.findAll();
        List<MeetingResponseDto> meetingResponseList = new ArrayList<>();
        for (Meeting meeting : meetings) {
            meetingResponseList.add(meetingMapper.convertToMeetingResponseDto(meeting));
        }
        return meetingResponseList;
    }

    @Override
    public MeetingResponseDto findMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(()-> new RuntimeException("The meeting with the id "+id+" was not found."));
        return meetingMapper.convertToMeetingResponseDto(meeting);
    }

    @Override
    public MeetingResponseDto updateMeetingById(Long id) {
        return null;
    }

    @Override
    public void deleteMeeting(Long id) {

    }
}
