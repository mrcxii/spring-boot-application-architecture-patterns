package dev.sivalabs.meetup4j.shared;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
class SpringEventPublisher implements DomainEventPublisher {
    private final ApplicationEventPublisher publisher;

    public SpringEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void publish(Collection<DomainEvent> events) {
        events.forEach(publisher::publishEvent);
    }
}
