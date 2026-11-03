package io.meeting.minutes.meetingservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "minutes_document")
public class MinutesDocument extends BaseEntity{
    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "meeting", referencedColumnName = "title")
    private Meeting meeting;
}
