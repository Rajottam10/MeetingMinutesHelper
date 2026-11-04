package io.meeting.minutes.meetingservice.dtos;

import io.meeting.minutes.meetingservice.constants.MeetingType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MeetingRequestDto {
    @NotBlank
    @Size(max = 255)
    private String title;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private MeetingType meetingType;

    private String meetingLink;
    private String meetingLocation;
    private String projectName;
    private String committeeName;

    @NotBlank
    @Email
    private String meetingOrganizer;

    @Email
    private String meetingChair;

    private Long meetingSeriesId;
    private List<Long> meetingParticipants;
    private List<Long> distributionRecipients;
    private List<String> agendaItems;
}