package dev.sivalabs.meetup4j.events.application.command;

import dev.sivalabs.meetup4j.events.application.command.dto.CancelEventCmd;
import dev.sivalabs.meetup4j.events.domain.repository.EventRepository;
import dev.sivalabs.meetup4j.shared.DomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CancelEventUseCase {
    private final EventRepository eventRepository;
    private final DomainEventPublisher eventPublisher;

    public CancelEventUseCase(EventRepository eventRepository,
                              DomainEventPublisher eventPublisher) {
        this.eventRepository = eventRepository;
        this.eventPublisher = eventPublisher;
    }

    public void cancelEvent(CancelEventCmd cmd) {
        var event = eventRepository.getByCode(cmd.eventCode());
        if(event.cancel()) {
            eventRepository.update(event);
            eventPublisher.publish(event.pullDomainEvents());
        }
    }
}
