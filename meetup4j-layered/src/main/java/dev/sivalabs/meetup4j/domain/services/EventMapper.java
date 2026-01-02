package dev.sivalabs.meetup4j.domain.services;

import dev.sivalabs.meetup4j.domain.entities.EventEntity;
import dev.sivalabs.meetup4j.domain.models.EventVM;
import org.springframework.stereotype.Component;

@Component
class EventMapper {

    EventVM toEventVM(EventEntity event) {
        return new EventVM(
                event.getId(),
                event.getCode(),
                event.getTitle(),
                event.getDescription(),
                event.getStartDatetime(),
                event.getEndDatetime(),
                event.getType(),
                event.getStatus(),
                event.getImageUrl(),
                event.getTicketPrice(),
                event.getCapacity(),
                event.getVenue(),
                event.getVirtualLink(),
                event.getRegistrationsCount());
    }
}
