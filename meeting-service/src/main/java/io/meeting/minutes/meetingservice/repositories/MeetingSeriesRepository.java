package io.meeting.minutes.meetingservice.repositories;

import io.meeting.minutes.meetingservice.entities.MeetingSeries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingSeriesRepository extends JpaRepository<MeetingSeries, Long> {
}