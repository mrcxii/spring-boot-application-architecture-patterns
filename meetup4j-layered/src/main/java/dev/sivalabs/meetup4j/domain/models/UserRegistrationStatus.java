package dev.sivalabs.meetup4j.domain.models;

import java.time.Instant;

public record UserRegistrationStatus(
        boolean registered,
        String registrationCode,
        Instant registeredAt) {

    public static UserRegistrationStatus notRegistered() {
        return new UserRegistrationStatus(false, null, null);
    }
}
