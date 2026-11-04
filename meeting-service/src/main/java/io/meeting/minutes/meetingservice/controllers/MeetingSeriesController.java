package io.meeting.minutes.meetingservice.controllers;

import io.meeting.minutes.meetingservice.dtos.SeriesRequestDto;
import io.meeting.minutes.meetingservice.dtos.SeriesResponseDto;
import io.meeting.minutes.meetingservice.dtos.SeriesScheduleResponseDto;
import io.meeting.minutes.meetingservice.services.MeetingSeriesService;
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
@RequestMapping("/series")
public class MeetingSeriesController {
    private final MeetingSeriesService meetingSeriesService;

    public MeetingSeriesController(MeetingSeriesService meetingSeriesService) {
        this.meetingSeriesService = meetingSeriesService;
    }

    @PostMapping("/create")
    public ResponseEntity<SeriesResponseDto> createSeries(@Valid @RequestBody SeriesRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingSeriesService.createSeries(request));
    }

    @GetMapping
    public ResponseEntity<List<SeriesResponseDto>> findAllSeries() {
        return ResponseEntity.ok(meetingSeriesService.getAllSeries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeriesResponseDto> findSeriesById(@PathVariable Long id) {
        return ResponseEntity.ok(meetingSeriesService.findSeriesById(id));
    }

    @GetMapping("/{id}/schedule")
    public ResponseEntity<SeriesScheduleResponseDto> getSchedule(@PathVariable Long id) {
        return ResponseEntity.ok(meetingSeriesService.getSchedule(id));
    }
}