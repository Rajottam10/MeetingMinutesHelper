package io.meeting.minutes.meetingservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SeriesScheduleResponseDto {
    private SeriesResponseDto series;
    private List<MeetingResponseDto> previousMeetings;
    private List<MeetingResponseDto> upcomingMeetings;
}