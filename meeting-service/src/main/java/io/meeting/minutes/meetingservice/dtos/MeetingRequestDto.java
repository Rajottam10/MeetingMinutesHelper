package io.meeting.minutes.meetingservice.dtos;

import io.meeting.minutes.meetingservice.constants.MeetingType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class MeetingRequestDto {
    @NotNull
    private String title;
    private String meetingLink;
    private String meetingLocation;
    private MeetingType meetingType;
    private List<Long> meetingParticipants;
    @NotNull
    private String meetingOrganizer;
    private String meetingChair;
}
