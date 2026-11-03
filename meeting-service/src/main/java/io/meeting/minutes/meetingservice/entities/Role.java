package io.meeting.minutes.meetingservice.entities;

import io.meeting.minutes.meetingservice.constants.RoleName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "role")
public class Role extends BaseEntity{
    @Column(name = "role_name")
    private RoleName roleName;
    @Column(name = "description")
    private String description;
}
