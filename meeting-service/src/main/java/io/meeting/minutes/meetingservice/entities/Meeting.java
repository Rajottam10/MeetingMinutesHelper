package io.meeting.minutes.meetingservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "meeting")
public class Meeting extends BaseEntity{
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start_date_time")
    private Date startDateTime;

    @Column(name = "meeting_link")
    private String meetingLink;

    @Column(name = "meeting_location")
    private String meetingLocation;

    @OneToMany
    @Column(name = "meeting_participants")
    private List<MeetingParticipant> meetingParticipants;

    @Column(name = "minutes_document")
    private MinutesDocument minutesDocument;
}
