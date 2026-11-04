package io.meeting.minutes.meetingservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeriesRequestDto {
    @NotBlank
    @Size(max = 255)
    private String seriesName;

    @Email
    private String ownerEmail;
}