package dev.sivalabs.meetup4j.shared;

import java.util.Collection;

public interface DomainEventPublisher {
    void publish(Collection<DomainEvent> events);
}
