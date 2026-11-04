package io.meeting.minutes.meetingservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeriesResponseDto {
    private Long id;
    private String seriesName;
    private String ownerEmail;
}