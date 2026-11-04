package io.meeting.minutes.meetingservice.controllers;

import io.meeting.minutes.meetingservice.dtos.MeetingRequestDto;
import io.meeting.minutes.meetingservice.dtos.MeetingResponseDto;
import io.meeting.minutes.meetingservice.services.MeetingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingController {
    private final MeetingService meetingService;

    private MeetingController(MeetingService meetingService){
        this.meetingService = meetingService;
    }

    @PostMapping("/create")
    private ResponseEntity<MeetingResponseDto> createMeeting(@Valid @RequestBody MeetingRequestDto meetingRequestDto){
        MeetingResponseDto meetingResponseDto = meetingService.createMeeting(meetingRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingResponseDto);
    }

    @GetMapping("/{id}")
    private ResponseEntity<MeetingResponseDto> findMeetingById(@PathVariable Long id){
        MeetingResponseDto meetingResponseDto = meetingService.findMeetingById(id);
        return ResponseEntity.ok(meetingResponseDto);
    }

    @GetMapping
    private ResponseEntity<List<MeetingResponseDto>> findAllMeetings(){
        List<MeetingResponseDto> meetingResponseDtos = meetingService.getAllMeetings();
        return ResponseEntity.ok(meetingResponseDtos);
    }
}
