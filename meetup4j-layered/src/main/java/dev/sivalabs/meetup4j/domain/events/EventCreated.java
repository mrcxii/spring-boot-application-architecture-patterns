package dev.sivalabs.meetup4j.domain.events;

import dev.sivalabs.meetup4j.domain.models.DomainEvent;

public record EventCreated(
        String code,
        String title,
        String description
) implements DomainEvent {}
