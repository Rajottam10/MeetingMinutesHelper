package io.meeting.minutes.meetingservice.mapper;

import io.meeting.minutes.meetingservice.dtos.MeetingRequestDto;
import io.meeting.minutes.meetingservice.dtos.MeetingResponseDto;
import io.meeting.minutes.meetingservice.entities.Meeting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MeetingMapper {

    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "meetingLink", target = "meetingLink"),
            @Mapping(source = "meetingLocation", target = "meetingLocation"),
            @Mapping(target = "meetingParticipants", ignore = true),
            @Mapping(target = "meetingOrganizer", ignore = true),
            @Mapping(target = "meetingChair", ignore = true)

    })
    public abstract Meeting convertToMeeting(MeetingRequestDto meetingRequestDto);

    @Mappings({
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "meetingType", target = "meetingType"),
            @Mapping(source = "meetingOrganizer.email", target = "meetingOrganizer")
    })
    public abstract MeetingResponseDto convertToMeetingResponseDto(Meeting meeting);
}
