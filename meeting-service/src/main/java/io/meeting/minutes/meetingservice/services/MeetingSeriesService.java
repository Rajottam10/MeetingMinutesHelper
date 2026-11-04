package io.meeting.minutes.meetingservice.services;

import io.meeting.minutes.meetingservice.dtos.SeriesRequestDto;
import io.meeting.minutes.meetingservice.dtos.SeriesResponseDto;
import io.meeting.minutes.meetingservice.dtos.SeriesScheduleResponseDto;

import java.util.List;

public interface MeetingSeriesService {
    SeriesResponseDto createSeries(SeriesRequestDto seriesRequestDto);

    List<SeriesResponseDto> getAllSeries();

    SeriesResponseDto findSeriesById(Long id);

    SeriesScheduleResponseDto getSchedule(Long id);
}