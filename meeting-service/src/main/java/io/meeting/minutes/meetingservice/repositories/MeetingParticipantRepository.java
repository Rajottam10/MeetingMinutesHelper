package io.meeting.minutes.meetingservice.repositories;

import io.meeting.minutes.meetingservice.entities.MeetingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, Long> {
    Optional<MeetingParticipant> findByMeetingIdAndUserId(Long meetingId, Long userId);
}