package dev.sivalabs.meetup4j.events.application.command;

import dev.sivalabs.meetup4j.events.application.command.dto.CreateEventCmd;
import dev.sivalabs.meetup4j.events.domain.model.Event;
import dev.sivalabs.meetup4j.events.domain.repository.EventRepository;
import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.shared.DomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateEventUseCase {
    private final EventRepository eventRepository;
    private final DomainEventPublisher eventPublisher;

    public CreateEventUseCase(EventRepository eventRepository,
                              DomainEventPublisher eventPublisher) {
        this.eventRepository = eventRepository;
        this.eventPublisher = eventPublisher;
    }

    public EventCode createEvent(CreateEventCmd cmd) {
        var event = Event.createDraft(
                cmd.details(),
                cmd.schedule(),
                cmd.type(),
                cmd.ticketPrice(),
                cmd.capacity(),
                cmd.location()
        );

        eventRepository.create(event);
        eventPublisher.publish(event.pullDomainEvents());
        return event.getCode();
    }
}
