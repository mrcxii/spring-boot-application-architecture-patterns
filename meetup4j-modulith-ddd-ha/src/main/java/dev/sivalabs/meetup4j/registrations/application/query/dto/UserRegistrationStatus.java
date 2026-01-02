package dev.sivalabs.meetup4j.registrations.application.query.dto;

import java.time.Instant;

public record UserRegistrationStatus(boolean registered, String registrationCode, Instant registeredAt) {
    public static UserRegistrationStatus notRegistered() {
        return new UserRegistrationStatus(false, null, null);
    }
}
