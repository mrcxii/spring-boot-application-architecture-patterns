package dev.sivalabs.meetup4j.events.rest;

import dev.sivalabs.meetup4j.events.domain.CancelEventCmd;
import dev.sivalabs.meetup4j.events.domain.CreateEventCmd;
import dev.sivalabs.meetup4j.events.domain.EventService;
import dev.sivalabs.meetup4j.events.domain.PublishEventCmd;
import dev.sivalabs.meetup4j.events.domain.models.EventVM;
import dev.sivalabs.meetup4j.events.rest.dto.CreateEventRequest;
import dev.sivalabs.meetup4j.events.rest.dto.CreateEventResponse;
import dev.sivalabs.meetup4j.events.rest.dto.EventsResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/events")
class EventsController {
    private static final Logger log = LoggerFactory.getLogger(EventsController.class);
    private final EventService eventService;

    EventsController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    EventsResponse findEvents() {
        var events = eventService.getUpcomingEvents();
        return new EventsResponse(events);
    }

    @GetMapping("/all")
    EventsResponse findAllEvents() {
        List<EventVM> events = eventService.findAllEvents();
        return new EventsResponse(events);
    }

    @GetMapping("/{eventCode}")
    ResponseEntity<EventVM> findEventByCode(@PathVariable String eventCode) {
        var event = eventService.getByCode(eventCode);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    ResponseEntity<CreateEventResponse> createEvent(
            @RequestBody @Valid CreateEventRequest request) {
        var cmd = new CreateEventCmd(
                request.title(),
                request.description(),
                request.startDatetime(),
                request.endDatetime(),
                request.type(),
                request.imageUrl(),
                request.ticketPrice(),
                request.capacity(),
                request.venue(),
                request.virtualLink());

        String eventCode = eventService.createEvent(cmd);
        log.info("Event created with code: {}", eventCode);
        return ResponseEntity.status(CREATED).body(new CreateEventResponse(eventCode));
    }

    @PatchMapping("/{eventCode}/publish")
    ResponseEntity<Void> publishEvent(@PathVariable String eventCode) {
        var cmd = new PublishEventCmd(eventCode);
        eventService.publishEvent(cmd);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{eventCode}")
    void cancelEvent(@PathVariable String eventCode) {
        var cmd = new CancelEventCmd(eventCode);
        eventService.cancelEvent(cmd);
    }
}
