package dev.sivalabs.meetup4j.events.domain;

import dev.sivalabs.meetup4j.events.domain.events.EventCancelled;
import dev.sivalabs.meetup4j.events.domain.events.EventCreated;
import dev.sivalabs.meetup4j.events.domain.events.EventPublished;
import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.shared.SpringEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final SpringEventPublisher eventPublisher;

    EventService(EventRepository eventRepository, SpringEventPublisher eventPublisher) {
        this.eventRepository = eventRepository;
        this.eventPublisher = eventPublisher;
    }

    public EventCode createEvent(CreateEventCmd cmd) {
        var event = EventEntity.createDraft(
                cmd.details(),
                cmd.schedule(),
                cmd.type(),
                cmd.ticketPrice(),
                cmd.capacity(),
                cmd.location()
        );

        eventRepository.save(event);
        eventPublisher.publish(new EventCreated(event.getCode().code(), event.getDetails().title(), event.getDetails().description()));
        return event.getCode();
    }

    public void cancelEvent(CancelEventCmd cmd) {
        EventEntity event = eventRepository.getByCode(cmd.eventCode());
        if(event.cancel()) {
            eventRepository.save(event);
            eventPublisher.publish(new EventCancelled(event.getCode().code(), event.getDetails().title(), event.getDetails().description()));
        }
    }

    public void publishEvent(PublishEventCmd cmd) {
        EventEntity event = eventRepository.getByCode(cmd.eventCode());
        if(event.publish()) {
            eventRepository.save(event);
            eventPublisher.publish(new EventPublished(event.getCode().code(), event.getDetails().title(), event.getDetails().description()));
        }
    }

    public void reserveSlotForEvent(EventId eventId) {
        var event = eventRepository.getEventById(eventId);
        if (event.getStatus() != EventStatus.PUBLISHED) {
            throw new EventSlotReservationException("This event is not open for registration");
        }

        if (event.isStarted()) {
            throw new EventSlotReservationException("Cannot register for past events");
        }

        if (!event.hasFreeSeats()) {
            throw new EventSlotReservationException("Event is full");
        }
        event.updateRegistrationsCount(event.getRegistrationsCount() + 1);
        eventRepository.save(event);
    }

    public void freeSlotForEvent(EventId eventId) {
        var event = eventRepository.getEventById(eventId);
        event.updateRegistrationsCount(event.getRegistrationsCount() - 1);
        eventRepository.save(event);
    }
}
