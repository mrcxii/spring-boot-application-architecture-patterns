package dev.sivalabs.meetup4j.events.domain.repository;

import dev.sivalabs.meetup4j.events.domain.model.Event;
import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.shared.exception.ResourceNotFoundException;

import java.util.Optional;

public interface EventRepository {

    void create(Event event);

    void update(Event event);

    Optional<Event> findById(EventId eventId);

    Optional<Event> findByCode(EventCode code);

    // -------- convenience methods ------------
    default Event getEventById(EventId eventId) {
        return this.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
    }

    default Event getByCode(EventCode eventCode) {
        return this.findByCode(eventCode)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with code: " + eventCode));
    }
}
