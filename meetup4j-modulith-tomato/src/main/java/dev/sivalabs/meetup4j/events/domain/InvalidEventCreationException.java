package dev.sivalabs.meetup4j.events.domain;

import dev.sivalabs.meetup4j.shared.DomainException;

public class InvalidEventCreationException extends DomainException {
    public InvalidEventCreationException(String message) {
        super(message);
    }
}
