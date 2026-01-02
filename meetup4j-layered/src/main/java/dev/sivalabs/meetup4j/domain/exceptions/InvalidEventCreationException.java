package dev.sivalabs.meetup4j.domain.exceptions;

public class InvalidEventCreationException extends DomainException {
    public InvalidEventCreationException(String message) {
        super(message);
    }
}
