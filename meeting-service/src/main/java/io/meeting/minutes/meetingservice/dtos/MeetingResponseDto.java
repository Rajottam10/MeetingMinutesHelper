package io.meeting.minutes.meetingservice.dtos;

import io.meeting.minutes.meetingservice.constants.MeetingStatus;
import io.meeting.minutes.meetingservice.constants.MeetingType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MeetingResponseDto {
    private Long id;
    private String title;
    private LocalDateTime startDateTime;
    private MeetingType meetingType;
    private MeetingStatus status;
    private String meetingLink;
    private String meetingLocation;
    private String projectName;
    private String committeeName;
    private String meetingOrganizer;
    private String meetingChair;
    private Long meetingSeriesId;
    private String meetingSeriesName;
    private List<String> agendaItems;
    private List<ParticipantResponseDto> meetingParticipants;
}