package dev.sivalabs.meetup4j.registrations.domain.event;

import dev.sivalabs.meetup4j.shared.DomainEvent;

public record RegistrationAdded(Long eventId, String attendeeEmail) implements DomainEvent {}
