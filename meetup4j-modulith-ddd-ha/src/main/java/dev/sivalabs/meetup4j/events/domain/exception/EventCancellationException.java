package dev.sivalabs.meetup4j.events.domain.exception;

import dev.sivalabs.meetup4j.shared.exception.DomainException;

public class EventCancellationException extends DomainException {
    public EventCancellationException(String message) {
        super(message);
    }
}
