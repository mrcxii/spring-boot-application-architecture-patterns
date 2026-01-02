package dev.sivalabs.meetup4j.events.domain.event;

import dev.sivalabs.meetup4j.shared.DomainEvent;

public record EventCancelled(String code, String title, String description) implements DomainEvent {
}
