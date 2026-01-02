package dev.sivalabs.meetup4j.events.application.command;

import dev.sivalabs.meetup4j.events.domain.exception.EventSlotReservationException;
import dev.sivalabs.meetup4j.events.domain.model.EventStatus;
import dev.sivalabs.meetup4j.events.domain.repository.EventRepository;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventSlotReservationUseCase {
    private final EventRepository eventRepository;

    EventSlotReservationUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
        eventRepository.update(event);
    }

    public void freeSlotForEvent(EventId eventId) {
        var event = eventRepository.getEventById(eventId);
        event.updateRegistrationsCount(event.getRegistrationsCount() - 1);
        eventRepository.update(event);
    }
}
