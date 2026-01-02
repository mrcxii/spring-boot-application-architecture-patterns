package dev.sivalabs.meetup4j.events.domain;

import dev.sivalabs.meetup4j.shared.DomainException;

public class EventCancellationException extends DomainException {
    public EventCancellationException(String message) {
        super(message);
    }
}
