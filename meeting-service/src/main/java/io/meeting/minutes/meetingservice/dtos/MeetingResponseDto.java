package io.meeting.minutes.meetingservice.dtos;

import io.meeting.minutes.meetingservice.constants.MeetingType;
import io.meeting.minutes.meetingservice.entities.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MeetingResponseDto {
    private String title;
    private MeetingType meetingType;
    private User meetingOrganizer;

}
