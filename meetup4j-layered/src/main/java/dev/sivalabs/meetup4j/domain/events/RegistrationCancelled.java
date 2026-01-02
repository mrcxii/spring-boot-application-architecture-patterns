package dev.sivalabs.meetup4j.domain.events;

import dev.sivalabs.meetup4j.domain.models.DomainEvent;

public record RegistrationCancelled(
        Long eventId,
        String attendeeEmail
) implements DomainEvent {}
