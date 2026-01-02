package dev.sivalabs.meetup4j.events.domain.events;

import dev.sivalabs.meetup4j.shared.DomainEvent;

public record EventCreated(
        String code,
        String title,
        String description
) implements DomainEvent {}
