package io.meeting.minutes.meetingservice.dtos;

import io.meeting.minutes.meetingservice.constants.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceUpdateRequestDto {
    @NotNull
    private AttendanceStatus attendanceStatus;
}