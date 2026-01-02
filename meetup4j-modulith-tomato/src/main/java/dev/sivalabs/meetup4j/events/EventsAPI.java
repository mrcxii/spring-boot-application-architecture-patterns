package dev.sivalabs.meetup4j.events;

import dev.sivalabs.meetup4j.events.domain.EventQueryService;
import dev.sivalabs.meetup4j.events.domain.EventService;
import dev.sivalabs.meetup4j.events.domain.models.EventVM;
import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsAPI {
    private final EventService eventService;
    private final EventQueryService eventQueryService;

    public EventsAPI(EventService eventService, EventQueryService eventQueryService) {
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
