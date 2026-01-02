package dev.sivalabs.meetup4j.events.domain.exception;

import dev.sivalabs.meetup4j.shared.exception.DomainException;

public class EventSlotReservationException extends DomainException {
    public EventSlotReservationException(String message) {
        super(message);
    }
}
