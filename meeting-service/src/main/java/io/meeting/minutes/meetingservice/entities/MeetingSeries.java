package io.meeting.minutes.meetingservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "meeting_series")
public class MeetingSeries extends BaseEntity{
    @Column(name = "series_name", nullable = false)
    private String seriesName;

    @OneToMany
    private Meeting meeting;

    @Column(name = "user")
    private User user;

    @Column(name = "meeting_participant")
    private MeetingParticipant meetingParticipant;

    @Column(name = "agenda_item")
    private AgendaItem agendaItem;
}
