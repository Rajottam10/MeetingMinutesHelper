package io.meeting.minutes.meetingservice.entities;

import io.meeting.minutes.meetingservice.constants.MeetingStatus;
import io.meeting.minutes.meetingservice.constants.MeetingType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "meeting")
public class Meeting extends BaseEntity {
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start_date_time", nullable = false)
    private LocalDateTime startDateTime;

    @Column(name = "meeting_link")
    private String meetingLink;

    @Enumerated(EnumType.STRING)
    @Column(name = "meeting_type", nullable = false)
    private MeetingType meetingType;

    @Column(name = "meeting_location")
    private String meetingLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MeetingStatus status = MeetingStatus.SCHEDULED;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "committee_name")
    private String committeeName;

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.PERSIST)
    private List<MeetingParticipant> meetingParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "meeting", cascade = CascadeType.PERSIST)
    private List<AgendaItem> agendaItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meeting_organizer_id", nullable = false)
    private User meetingOrganizer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_chair_id")
    private User meetingChair;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_series_id")
    private MeetingSeries meetingSeries;
}