package io.meeting.minutes.meetingservice.repositories;

import io.meeting.minutes.meetingservice.constants.MeetingStatus;
import io.meeting.minutes.meetingservice.entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    List<Meeting> findByMeetingSeriesIdAndStartDateTimeBeforeOrderByStartDateTimeDesc(
            Long seriesId, LocalDateTime now);

    List<Meeting> findByMeetingSeriesIdAndStartDateTimeGreaterThanEqualOrderByStartDateTimeAsc(
            Long seriesId, LocalDateTime now);

    @Query("""
            select m from Meeting m
            where (:titlePattern is null or lower(m.title) like :titlePattern)
              and (:fromDate is null or m.startDateTime >= :fromDate)
              and (:toDate is null or m.startDateTime <= :toDate)
              and (:seriesId is null or m.meetingSeries.id = :seriesId)
              and (:status is null or m.status = :status)
              and (:participantId is null or exists (
                    select 1 from MeetingParticipant p
                    where p.meeting = m and p.user.id = :participantId))
            order by m.startDateTime desc
            """)
    List<Meeting> search(@Param("titlePattern") String titlePattern,
                         @Param("fromDate") LocalDateTime fromDate,
                         @Param("toDate") LocalDateTime toDate,
                         @Param("seriesId") Long seriesId,
                         @Param("status") MeetingStatus status,
                         @Param("participantId") Long participantId);
}