package dev.sivalabs.meetup4j.events.domain;

import dev.sivalabs.meetup4j.shared.DomainException;

public class EventSlotReservationException extends DomainException {
    public EventSlotReservationException(String message) {
        super(message);
    }
}
