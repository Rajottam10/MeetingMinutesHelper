package io.meeting.minutes.meetingservice.services;

import io.meeting.minutes.meetingservice.dtos.MeetingRequestDto;
import io.meeting.minutes.meetingservice.dtos.MeetingResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeetingService {
    MeetingResponseDto createMeeting(MeetingRequestDto meetingRequestDto);
    List<MeetingResponseDto> getAllMeetings();
    MeetingResponseDto findMeetingById(Long id);
    MeetingResponseDto updateMeetingById(Long id);
    void deleteMeeting(Long id);
}
