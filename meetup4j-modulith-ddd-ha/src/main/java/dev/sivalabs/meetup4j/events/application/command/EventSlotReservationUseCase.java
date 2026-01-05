package dev.sivalabs.meetup4j.events.application.command;

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
        event.reserveSlot();
        eventRepository.update(event);
    }

    public void freeSlotForEvent(EventId eventId) {
        var event = eventRepository.getEventById(eventId);
        event.freeSlot();
        eventRepository.update(event);
    }
}
