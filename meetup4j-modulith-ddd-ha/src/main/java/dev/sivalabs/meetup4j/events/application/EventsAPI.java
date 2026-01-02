package dev.sivalabs.meetup4j.events.application;

import dev.sivalabs.meetup4j.events.application.command.EventSlotReservationUseCase;
import dev.sivalabs.meetup4j.events.application.query.EventQueryService;
import dev.sivalabs.meetup4j.events.application.query.dto.EventVM;
import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import org.springframework.modulith.NamedInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NamedInterface
public class EventsAPI {
    private final EventSlotReservationUseCase eventService;
    private final EventQueryService eventQueryService;

    public EventsAPI(EventSlotReservationUseCase eventService, EventQueryService eventQueryService) {
        this.eventService = eventService;
        this.eventQueryService = eventQueryService;
    }

    public List<EventVM> getEventsByIds(List<EventId> eventIds) {
        return eventQueryService.getEventsByIds(eventIds);
    }

    public EventVM getEventByCode(EventCode eventCode) {
        return eventQueryService.getByCode(eventCode);
    }

    public void reserveSlotForEvent(EventId eventId) {
        eventService.reserveSlotForEvent(eventId);
    }

    public void freeSlotForEvent(EventId eventId) {
        eventService.freeSlotForEvent(eventId);
    }
}
