package io.meeting.minutes.meetingservice.controllers;

import io.meeting.minutes.meetingservice.constants.MeetingStatus;
import io.meeting.minutes.meetingservice.dtos.AttendanceUpdateRequestDto;
import io.meeting.minutes.meetingservice.dtos.MeetingRequestDto;
import io.meeting.minutes.meetingservice.dtos.MeetingResponseDto;
import io.meeting.minutes.meetingservice.services.MeetingService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingController {
    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping("/create")
    public ResponseEntity<MeetingResponseDto> createMeeting(@Valid @RequestBody MeetingRequestDto meetingRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingService.createMeeting(meetingRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeetingResponseDto> findMeetingById(@PathVariable Long id) {
        return ResponseEntity.ok(meetingService.findMeetingById(id));
    }

    @GetMapping
    public ResponseEntity<List<MeetingResponseDto>> findAllMeetings() {
        return ResponseEntity.ok(meetingService.getAllMeetings());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MeetingResponseDto>> searchMeetings(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(required = false) Long seriesId,
            @RequestParam(required = false) Long participantId,
            @RequestParam(required = false) MeetingStatus status) {
        return ResponseEntity.ok(meetingService.searchMeetings(title, from, to, seriesId, participantId, status));
    }

    @PatchMapping("/{meetingId}/participants/{userId}/attendance")
    public ResponseEntity<MeetingResponseDto> updateAttendance(
            @PathVariable Long meetingId,
            @PathVariable Long userId,
            @Valid @RequestBody AttendanceUpdateRequestDto request) {
        return ResponseEntity.ok(meetingService.updateAttendance(meetingId, userId, request.getAttendanceStatus()));
    }
}