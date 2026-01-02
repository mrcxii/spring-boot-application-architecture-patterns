package dev.sivalabs.meetup4j.domain.exceptions;

public class EventCancellationException extends DomainException {
    public EventCancellationException(String message) {
        super(message);
    }
}
