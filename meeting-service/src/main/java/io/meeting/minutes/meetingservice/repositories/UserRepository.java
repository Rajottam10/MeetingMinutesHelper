package io.meeting.minutes.meetingservice.repositories;

import io.meeting.minutes.meetingservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
