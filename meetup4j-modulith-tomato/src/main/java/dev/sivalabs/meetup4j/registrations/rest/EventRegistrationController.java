package dev.sivalabs.meetup4j.registrations.rest;

import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.registrations.domain.*;
import dev.sivalabs.meetup4j.registrations.domain.vo.Email;
import dev.sivalabs.meetup4j.registrations.domain.vo.RegistrationCode;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
class EventRegistrationController {
    private static final Logger log = LoggerFactory.getLogger(EventRegistrationController.class);

    private final EventRegistrationService eventRegistrationService;
    private final EventRegistrationQueryService eventRegistrationQueryService;

    EventRegistrationController(
            EventRegistrationService eventRegistrationService,
            EventRegistrationQueryService eventRegistrationQueryService) {
        this.eventRegistrationService = eventRegistrationService;
        this.eventRegistrationQueryService = eventRegistrationQueryService;
    }

    @PostMapping("/registrations")
    ResponseEntity<EventRegistrationResponse> registerForEvent(
            @RequestBody @Valid EventRegistrationRequest request) {
        var cmd = new RegisterAttendeeCmd(
                request.eventCode(),
                request.attendeeName(),
                request.attendeeEmail());
        var registrationCode = eventRegistrationService.registerForEvent(cmd);
        log.info("Registration created with code: {}", registrationCode);
        EventRegistrationResponse response = new EventRegistrationResponse(registrationCode.code());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/registrations/{registrationCode}")
    ResponseEntity<Void> cancelRegistration(@PathVariable RegistrationCode registrationCode) {
        var cmd = new CancelRegistrationCmd(registrationCode);
        eventRegistrationService.cancelRegistration(cmd);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/events/{eventCode}/registrations")
    EventRegistrations getEventRegistrations(@PathVariable EventCode eventCode) {
        var registrations = eventRegistrationQueryService.getEventRegistrations(eventCode);
        return new EventRegistrations(registrations);
    }

    @GetMapping("/events/{eventCode}/registrations/attendees")
    AttendeesVM getEventAttendees(@PathVariable EventCode eventCode) {
        return eventRegistrationQueryService.getEventAttendees(eventCode);
    }

    @GetMapping("/events/{eventCode}/registrations/status")
    UserRegistrationStatus getUserEventRegistrationStatus(@PathVariable EventCode eventCode,
                                                          @RequestParam Email attendeeEmail) {
        return eventRegistrationQueryService.getRegistrationStatus(
                eventCode, attendeeEmail);
    }
}
