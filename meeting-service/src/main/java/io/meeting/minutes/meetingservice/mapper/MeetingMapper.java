package io.meeting.minutes.meetingservice.mapper;

import io.meeting.minutes.meetingservice.dtos.MeetingRequestDto;
import io.meeting.minutes.meetingservice.dtos.MeetingResponseDto;
import io.meeting.minutes.meetingservice.dtos.ParticipantResponseDto;
import io.meeting.minutes.meetingservice.dtos.SeriesResponseDto;
import io.meeting.minutes.meetingservice.entities.AgendaItem;
import io.meeting.minutes.meetingservice.entities.Meeting;
import io.meeting.minutes.meetingservice.entities.MeetingParticipant;
import io.meeting.minutes.meetingservice.entities.MeetingSeries;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MeetingMapper {

    // Relationships are resolved in the service, not mapped here.
    @Mapping(target = "meetingParticipants", ignore = true)
    @Mapping(target = "agendaItems", ignore = true)
    @Mapping(target = "meetingOrganizer", ignore = true)
    @Mapping(target = "meetingChair", ignore = true)
    @Mapping(target = "meetingSeries", ignore = true)
    public abstract Meeting convertToMeeting(MeetingRequestDto meetingRequestDto);

    @Mapping(source = "meetingOrganizer.email", target = "meetingOrganizer")
    @Mapping(source = "meetingChair.email", target = "meetingChair")
    @Mapping(source = "meetingSeries.id", target = "meetingSeriesId")
    @Mapping(source = "meetingSeries.seriesName", target = "meetingSeriesName")
    public abstract MeetingResponseDto convertToMeetingResponseDto(Meeting meeting);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.email", target = "email")
    public abstract ParticipantResponseDto convertToParticipantResponseDto(MeetingParticipant participant);

    @Mapping(source = "user.email", target = "ownerEmail")
    public abstract SeriesResponseDto convertToSeriesResponseDto(MeetingSeries series);

    // Used by MapStruct for the agenda list: AgendaItem -> its name.
    protected String agendaItemToName(AgendaItem agendaItem) {
        return agendaItem.getName();
    }
}