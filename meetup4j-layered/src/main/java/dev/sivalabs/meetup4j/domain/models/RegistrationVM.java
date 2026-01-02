package dev.sivalabs.meetup4j.domain.models;

import java.time.Instant;

public record RegistrationVM(
        Long id,
        String code,
        Long eventId,
        String eventCode,
        String attendeeName,
        String attendeeEmail,
        Instant registeredAt) {}
