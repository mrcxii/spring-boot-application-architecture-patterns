package dev.sivalabs.meetup4j.registrations.application.query.dto;

import java.time.Instant;

public record RegistrationVM(
        Long id,
        String code,
        Long eventId,
        String eventCode,
        String attendeeName,
        String attendeeEmail,
        Instant registeredAt) {}
