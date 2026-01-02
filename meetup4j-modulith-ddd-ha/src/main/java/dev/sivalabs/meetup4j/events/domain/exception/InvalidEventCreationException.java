package dev.sivalabs.meetup4j.events.domain.exception;

import dev.sivalabs.meetup4j.shared.exception.DomainException;

public class InvalidEventCreationException extends DomainException {
    public InvalidEventCreationException(String message) {
        super(message);
    }
}
