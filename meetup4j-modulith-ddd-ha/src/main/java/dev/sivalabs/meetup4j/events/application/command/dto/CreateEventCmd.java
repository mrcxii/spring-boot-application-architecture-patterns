package dev.sivalabs.meetup4j.events.application.command.dto;

import dev.sivalabs.meetup4j.events.domain.exception.InvalidEventCreationException;
import dev.sivalabs.meetup4j.events.domain.model.EventType;
import dev.sivalabs.meetup4j.events.domain.vo.*;

public record CreateEventCmd(
        EventDetails details,
        Schedule schedule,
        EventType type,
        TicketPrice ticketPrice,
        Capacity capacity,
        EventLocation location) {

    public CreateEventCmd {
        if (type == EventType.OFFLINE && (location.venue() == null || location.venue().isBlank())) {
            throw new InvalidEventCreationException("In-person events must have a venue");
        }
        if (type == EventType.ONLINE && (location.virtualLink() == null || location.virtualLink().isBlank())) {
            throw new InvalidEventCreationException("Online events must have a virtual link");
        }
    }
}
