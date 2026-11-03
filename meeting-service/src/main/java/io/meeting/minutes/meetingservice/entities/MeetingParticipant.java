package io.meeting.minutes.meetingservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "meeting_participant")
public class MeetingParticipant extends BaseEntity {
    @Column(name = "name")
    private String name;

    @ManyToOne
    private Meeting meeting;

    private User user;
}
