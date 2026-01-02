package dev.sivalabs.meetup4j.events.application.command;

import dev.sivalabs.meetup4j.events.application.command.dto.PublishEventCmd;
import dev.sivalabs.meetup4j.events.domain.repository.EventRepository;
import dev.sivalabs.meetup4j.shared.DomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PublishEventUseCase {
    private final EventRepository eventRepository;
    private final DomainEventPublisher eventPublisher;

    public PublishEventUseCase(EventRepository eventRepository, DomainEventPublisher eventPublisher) {
        this.eventRepository = eventRepository;
        this.eventPublisher = eventPublisher;
    }

    public void publishEvent(PublishEventCmd cmd) {
        var event = eventRepository.getByCode(cmd.eventCode());
        if(event.publish()) {
            eventRepository.update(event);
            eventPublisher.publish(event.pullDomainEvents());
        }
    }
}
