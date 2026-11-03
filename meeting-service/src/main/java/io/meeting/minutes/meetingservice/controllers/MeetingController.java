package io.meeting.minutes.meetingservice.controllers;

import io.meeting.minutes.meetingservice.dtos.MeetingRequestDto;
import io.meeting.minutes.meetingservice.services.MeetingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meeting")
public class MeetingController {
    private final MeetingService meetingService;

    private MeetingController(MeetingService meetingService){
        this.meetingService = meetingService;
    }

    @PostMapping("/create")
    private ResponseEntity<String> createMeeting(MeetingRequestDto meetingRequestDto){
        meetingService.createMeeting(meetingRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Meeting created Successfully.");
    }
}
