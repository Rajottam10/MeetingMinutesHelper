package io.meeting.minutes.meetingservice.services.impl;

import io.meeting.minutes.meetingservice.dtos.MeetingResponseDto;
import io.meeting.minutes.meetingservice.dtos.SeriesRequestDto;
import io.meeting.minutes.meetingservice.dtos.SeriesResponseDto;
import io.meeting.minutes.meetingservice.dtos.SeriesScheduleResponseDto;
import io.meeting.minutes.meetingservice.entities.MeetingSeries;
import io.meeting.minutes.meetingservice.exceptions.ResourceNotFoundException;
import io.meeting.minutes.meetingservice.mapper.MeetingMapper;
import io.meeting.minutes.meetingservice.repositories.MeetingRepository;
import io.meeting.minutes.meetingservice.repositories.MeetingSeriesRepository;
import io.meeting.minutes.meetingservice.repositories.UserRepository;
import io.meeting.minutes.meetingservice.services.MeetingSeriesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingSeriesServiceImpl implements MeetingSeriesService {
    private final MeetingSeriesRepository meetingSeriesRepository;
    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;
    private final MeetingMapper meetingMapper;

    public MeetingSeriesServiceImpl(MeetingSeriesRepository meetingSeriesRepository,
                                    MeetingRepository meetingRepository,
                                    UserRepository userRepository,
                                    MeetingMapper meetingMapper) {
        this.meetingSeriesRepository = meetingSeriesRepository;
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
        this.meetingMapper = meetingMapper;
    }

    @Override
    @Transactional
    public SeriesResponseDto createSeries(SeriesRequestDto dto) {
        MeetingSeries series = new MeetingSeries();
        series.setSeriesName(dto.getSeriesName().trim());
        if (StringUtils.hasText(dto.getOwnerEmail())) {
            series.setUser(userRepository.findByEmail(dto.getOwnerEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("No user was found for the owner email.")));
        }
        return meetingMapper.convertToSeriesResponseDto(meetingSeriesRepository.save(series));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SeriesResponseDto> getAllSeries() {
        return meetingSeriesRepository.findAll().stream()
                .map(meetingMapper::convertToSeriesResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SeriesResponseDto findSeriesById(Long id) {
        return meetingMapper.convertToSeriesResponseDto(getSeriesOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public SeriesScheduleResponseDto getSchedule(Long id) {
        MeetingSeries series = getSeriesOrThrow(id);
        LocalDateTime now = LocalDateTime.now();

        List<MeetingResponseDto> previous = meetingRepository
                .findByMeetingSeriesIdAndStartDateTimeBeforeOrderByStartDateTimeDesc(id, now).stream()
                .map(meetingMapper::convertToMeetingResponseDto)
                .toList();
        List<MeetingResponseDto> upcoming = meetingRepository
                .findByMeetingSeriesIdAndStartDateTimeGreaterThanEqualOrderByStartDateTimeAsc(id, now).stream()
                .map(meetingMapper::convertToMeetingResponseDto)
                .toList();

        SeriesScheduleResponseDto schedule = new SeriesScheduleResponseDto();
        schedule.setSeries(meetingMapper.convertToSeriesResponseDto(series));
        schedule.setPreviousMeetings(previous);
        schedule.setUpcomingMeetings(upcoming);
        return schedule;
    }

    private MeetingSeries getSeriesOrThrow(Long id) {
        return meetingSeriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "The meeting series with the id " + id + " was not found."));
    }
}