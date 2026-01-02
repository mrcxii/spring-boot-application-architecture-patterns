package dev.sivalabs.meetup4j.events.interfaces.rest;

import dev.sivalabs.meetup4j.events.application.command.CancelEventUseCase;
import dev.sivalabs.meetup4j.events.application.command.CreateEventUseCase;
import dev.sivalabs.meetup4j.events.application.command.PublishEventUseCase;
import dev.sivalabs.meetup4j.events.application.command.dto.CancelEventCmd;
import dev.sivalabs.meetup4j.events.application.command.dto.CreateEventCmd;
import dev.sivalabs.meetup4j.events.application.command.dto.PublishEventCmd;
import dev.sivalabs.meetup4j.events.application.query.EventQueryService;
import dev.sivalabs.meetup4j.events.application.query.dto.EventVM;
import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
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
    private final CreateEventUseCase createEventUseCase;
    private final PublishEventUseCase publishEventUseCase;
    private final CancelEventUseCase cancelEventUseCase;
    private final EventQueryService eventQueryService;

    EventsController(CreateEventUseCase createEventUseCase, PublishEventUseCase publishEventUseCase, CancelEventUseCase cancelEventUseCase, EventQueryService eventQueryService) {
        this.createEventUseCase = createEventUseCase;
        this.publishEventUseCase = publishEventUseCase;
        this.cancelEventUseCase = cancelEventUseCase;
        this.eventQueryService = eventQueryService;
    }

    @GetMapping("")
    EventsResponse findEvents() {
        var events = eventQueryService.getUpcomingEvents();
        return new EventsResponse(events);
    }

    @GetMapping("/all")
    EventsResponse findAllEvents() {
        List<EventVM> events = eventQueryService.findAllEvents();
        return new EventsResponse(events);
    }

    @GetMapping("/{eventCode}")
    ResponseEntity<EventVM> findEventByCode(@PathVariable EventCode eventCode) {
        var event = eventQueryService.getByCode(eventCode);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    ResponseEntity<CreateEventResponse> createEvent(@RequestBody @Valid CreateEventRequest request) {
        var cmd = new CreateEventCmd(
                request.details(),
                request.schedule(),
                request.type(),
                request.ticketPrice(),
                request.capacity(),
                request.location()
        );
        EventCode eventCode = createEventUseCase.createEvent(cmd);
        log.info("Event created with code: {}", eventCode);
        return ResponseEntity.status(CREATED).body(new CreateEventResponse(eventCode.code()));
    }

    @PatchMapping("/{eventCode}/publish")
    ResponseEntity<Void> publishEvent(@PathVariable EventCode eventCode) {
        var cmd = new PublishEventCmd(eventCode);
        publishEventUseCase.publishEvent(cmd);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{eventCode}")
    ResponseEntity<Void> cancelEvent(@PathVariable EventCode eventCode) {
        var cmd = new CancelEventCmd(eventCode);
        cancelEventUseCase.cancelEvent(cmd);
        return ResponseEntity.ok().build();
    }
}
