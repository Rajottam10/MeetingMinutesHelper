package io.meeting.minutes.meetingservice.dtos;

import io.meeting.minutes.meetingservice.constants.AttendanceStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParticipantResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private AttendanceStatus attendanceStatus;
}