package dev.sivalabs.meetup4j.registrations.domain.events;

import dev.sivalabs.meetup4j.shared.DomainEvent;

public record RegistrationCancelled(
        Long eventId,
        String attendeeEmail
) implements DomainEvent {}
