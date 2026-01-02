package dev.sivalabs.meetup4j.registrations.domain;

import dev.sivalabs.meetup4j.shared.DomainException;

public class RegistrationCancellationException extends DomainException {
    public RegistrationCancellationException(String message) {
        super(message);
    }
}
