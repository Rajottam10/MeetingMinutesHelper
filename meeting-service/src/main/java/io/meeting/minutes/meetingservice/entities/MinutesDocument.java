package io.meeting.minutes.meetingservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "minutes_document")
public class MinutesDocument extends BaseEntity{
    @Column(name = "name")
    private String name;

    @Column(name ="meeting")
    private Meeting meeting;
}
