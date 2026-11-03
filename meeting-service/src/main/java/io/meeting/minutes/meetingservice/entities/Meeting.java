package io.meeting.minutes.meetingservice.entities;

import io.meeting.minutes.meetingservice.constants.MeetingType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "meeting")
public class Meeting extends BaseEntity{
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "meeting_link")
    private String meetingLink;

    @Column(name = "meeting_type")
    private MeetingType meetingType;

    @Column(name = "meeting_location")
    private String meetingLocation;

    @OneToMany
    @Column(name = "meeting_participants")
    private List<MeetingParticipant> meetingParticipants;

    @OneToOne
    @JoinColumn(name = "meeting_organizer", referencedColumnName = "email")
    private User meetingOrganizer;

    @OneToOne
    @JoinColumn(name = "meeting_chair", referencedColumnName = "email")
    private User meetingChair;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_series_id")
    private MeetingSeries meetingSeries;
}
