package io.meeting.minutes.meetingservice.repositories;

import io.meeting.minutes.meetingservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
