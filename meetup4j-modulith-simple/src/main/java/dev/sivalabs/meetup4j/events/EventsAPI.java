package dev.sivalabs.meetup4j.events;

import dev.sivalabs.meetup4j.events.domain.EventService;
import dev.sivalabs.meetup4j.events.domain.models.EventVM;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsAPI {
    private final EventService eventService;

    public EventsAPI(EventService eventService) {
        this.eventService = eventService;
    }

    public List<EventVM> getEventsByIds(List<Long> eventIds) {
        return eventService.getEventsByIds(eventIds);
    }

    public EventVM getEventByCode(String eventCode) {
        return eventService.getByCode(eventCode);
    }

    public void reserveSlotForEvent(Long eventId) {
        eventService.reserveSlotForEvent(eventId);
    }

    public void freeSlotForEvent(Long eventId) {
        eventService.freeSlotForEvent(eventId);
    }
}
