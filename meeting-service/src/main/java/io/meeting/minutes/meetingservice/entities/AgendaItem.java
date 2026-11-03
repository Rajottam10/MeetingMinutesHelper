package io.meeting.minutes.meetingservice.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AgendaItem extends BaseEntity{
    private String name;
}
