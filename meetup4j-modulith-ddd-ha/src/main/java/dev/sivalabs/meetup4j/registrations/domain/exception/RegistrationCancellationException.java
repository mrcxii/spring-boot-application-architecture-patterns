package dev.sivalabs.meetup4j.registrations.domain.exception;

import dev.sivalabs.meetup4j.shared.exception.DomainException;

public class RegistrationCancellationException extends DomainException {
    public RegistrationCancellationException(String message) {
        super(message);
    }
}
