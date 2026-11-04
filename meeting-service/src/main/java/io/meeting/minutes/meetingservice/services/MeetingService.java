package io.meeting.minutes.meetingservice.services;

import io.meeting.minutes.meetingservice.constants.AttendanceStatus;
import io.meeting.minutes.meetingservice.constants.MeetingStatus;
import io.meeting.minutes.meetingservice.dtos.MeetingRequestDto;
import io.meeting.minutes.meetingservice.dtos.MeetingResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingService {
    MeetingResponseDto createMeeting(MeetingRequestDto meetingRequestDto);

    List<MeetingResponseDto> getAllMeetings();

    MeetingResponseDto findMeetingById(Long id);

    List<MeetingResponseDto> searchMeetings(String title, LocalDateTime from, LocalDateTime to,
                                            Long seriesId, Long participantId, MeetingStatus status);

    MeetingResponseDto updateAttendance(Long meetingId, Long userId, AttendanceStatus attendanceStatus);
}