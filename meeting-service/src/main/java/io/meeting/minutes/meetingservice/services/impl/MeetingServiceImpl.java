package io.meeting.minutes.meetingservice.services.impl;

import io.meeting.minutes.meetingservice.dtos.MeetingRequestDto;
import io.meeting.minutes.meetingservice.dtos.MeetingResponseDto;
import io.meeting.minutes.meetingservice.entities.Meeting;
import io.meeting.minutes.meetingservice.mapper.MeetingMapper;
import io.meeting.minutes.meetingservice.repositories.MeetingRepository;
import io.meeting.minutes.meetingservice.services.MeetingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingMapper meetingMapper;

    private MeetingServiceImpl(MeetingRepository meetingRepository, MeetingMapper meetingMapper){
        this.meetingRepository = meetingRepository;
        this.meetingMapper = meetingMapper;
    }

    @Override
    public MeetingResponseDto createMeeting(MeetingRequestDto meetingRequestDto) {
        Meeting meeting = meetingMapper.convertToMeeting(meetingRequestDto);
        meeting.setStartDateTime(LocalDateTime.now());
        return meetingMapper.convertToMeetingResponseDto(meeting);
    }

    private void validateRequest(MeetingRequestDto meetingRequestDto){
        // todo: validate each fields of requestDto by tallying it with repository
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
