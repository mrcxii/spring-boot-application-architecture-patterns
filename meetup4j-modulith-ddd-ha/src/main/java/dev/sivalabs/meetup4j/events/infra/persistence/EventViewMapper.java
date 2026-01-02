package dev.sivalabs.meetup4j.events.infra.persistence;

import dev.sivalabs.meetup4j.events.application.query.dto.EventVM;
import org.springframework.stereotype.Component;

@Component
class EventViewMapper {

    EventVM toEventVM(EventEntity event) {
        return new EventVM(
                event.getId().id(),
                event.getCode().code(),
                event.getDetails().title(),
                event.getDetails().description(),
                event.getSchedule().startDatetime(),
                event.getSchedule().endDatetime(),
                event.getType(),
                event.getStatus(),
                event.getDetails().imageUrl(),
                event.getTicketPrice().amount(),
                event.getCapacity().value(),
                event.getLocation().venue(),
                event.getLocation().virtualLink(),
                event.getRegistrationsCount());
    }
}
