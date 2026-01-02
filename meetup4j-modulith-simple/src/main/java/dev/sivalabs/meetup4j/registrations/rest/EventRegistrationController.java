package dev.sivalabs.meetup4j.registrations.rest;

import dev.sivalabs.meetup4j.registrations.domain.*;
import dev.sivalabs.meetup4j.registrations.rest.dto.EventRegistrationRequest;
import dev.sivalabs.meetup4j.registrations.rest.dto.EventRegistrationResponse;
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

    private final EventRegistrationService registrationService;

    EventRegistrationController(
            EventRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registrations")
    ResponseEntity<EventRegistrationResponse> registerForEvent(@RequestBody @Valid EventRegistrationRequest request) {
        var cmd = new RegisterAttendeeCmd(request.eventCode(), request.attendeeName(), request.attendeeEmail());
        var registrationCode = registrationService.registerForEvent(cmd);
        log.info("Registration created with code: {}", registrationCode);
        var response = new EventRegistrationResponse(registrationCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/registrations/{registrationCode}")
    void cancelRegistration(@PathVariable String registrationCode) {
        var cmd = new CancelRegistrationCmd(registrationCode);
        registrationService.cancelRegistration(cmd);
    }

    @GetMapping("/events/{eventCode}/registrations")
    EventRegistrations getEventRegistrations(@PathVariable String eventCode) {
        var registrations = registrationService.getEventRegistrations(eventCode);
        return new EventRegistrations(registrations);
    }

    @GetMapping("/events/{eventCode}/registrations/attendees")
    AttendeesVM getEventAttendees(@PathVariable String eventCode) {
        return registrationService.getEventAttendees(eventCode);
    }

    @GetMapping("/events/{eventCode}/registrations/status")
    UserRegistrationStatus getUserEventRegistrationStatus(@PathVariable String eventCode,
                                                          @RequestParam String attendeeEmail) {
        return registrationService.getRegistrationStatus(eventCode, attendeeEmail);
    }
}
