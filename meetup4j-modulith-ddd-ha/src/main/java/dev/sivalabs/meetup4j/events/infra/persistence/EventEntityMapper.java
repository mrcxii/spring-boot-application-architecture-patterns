package dev.sivalabs.meetup4j.events.infra.persistence;

import dev.sivalabs.meetup4j.events.domain.model.Event;
import org.springframework.stereotype.Component;

@Component
class EventEntityMapper {

    Event toEvent(EventEntity entity) {
        return new Event(
                entity.getId(),
                entity.getCode(),
                entity.getDetails(),
                entity.getSchedule(),
                entity.getType(),
                entity.getStatus(),
                entity.getTicketPrice(),
                entity.getCapacity(),
                entity.getLocation(),
                entity.getRegistrationsCount());
    }
}
