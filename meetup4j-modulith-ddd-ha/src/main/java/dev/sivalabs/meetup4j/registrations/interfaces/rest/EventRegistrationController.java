package dev.sivalabs.meetup4j.registrations.interfaces.rest;

import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.registrations.application.command.CancelRegistrationUseCase;
import dev.sivalabs.meetup4j.registrations.application.command.RegisterAttendeeUseCase;
import dev.sivalabs.meetup4j.registrations.application.command.dto.CancelRegistrationCmd;
import dev.sivalabs.meetup4j.registrations.application.command.dto.RegisterAttendeeCmd;
import dev.sivalabs.meetup4j.registrations.application.query.RegistrationQueryService;
import dev.sivalabs.meetup4j.registrations.application.query.dto.AttendeesVM;
import dev.sivalabs.meetup4j.registrations.application.query.dto.EventRegistrations;
import dev.sivalabs.meetup4j.registrations.application.query.dto.UserRegistrationStatus;
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
    private final RegisterAttendeeUseCase registerAttendeeUseCase;
    private final CancelRegistrationUseCase cancelRegistrationUseCase;
    private final RegistrationQueryService registrationQueryService;

    EventRegistrationController(RegisterAttendeeUseCase registerAttendeeUseCase, CancelRegistrationUseCase cancelRegistrationUseCase, RegistrationQueryService registrationQueryService) {
        this.registerAttendeeUseCase = registerAttendeeUseCase;
        this.cancelRegistrationUseCase = cancelRegistrationUseCase;
        this.registrationQueryService = registrationQueryService;
    }

    @PostMapping("/registrations")
    ResponseEntity<EventRegistrationResponse> registerForEvent(
            @RequestBody @Valid EventRegistrationRequest request) {
        var cmd = new RegisterAttendeeCmd(
                request.eventCode(),
                request.attendeeName(),
                request.attendeeEmail());

        var registrationCode = registerAttendeeUseCase.registerForEvent(cmd);
        log.info("Registration created with code: {}", registrationCode);
        EventRegistrationResponse response = new EventRegistrationResponse(registrationCode.code());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/registrations/{registrationCode}")
    ResponseEntity<Void> cancelRegistration(@PathVariable RegistrationCode registrationCode) {
        var cmd = new CancelRegistrationCmd(registrationCode);
        cancelRegistrationUseCase.cancelRegistration(cmd);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/events/{eventCode}/registrations")
    EventRegistrations getEventRegistrations(@PathVariable EventCode eventCode) {

        var registrations = registrationQueryService.getEventRegistrations(eventCode);
        return new EventRegistrations(registrations);
    }

    @GetMapping("/events/{eventCode}/registrations/attendees")
    AttendeesVM getEventAttendees(@PathVariable EventCode eventCode) {
        return registrationQueryService.getEventAttendees(eventCode);
    }

    @GetMapping("/events/{eventCode}/registrations/status")
    UserRegistrationStatus getUserEventRegistrationStatus(@PathVariable EventCode eventCode,
                                                          @RequestParam Email attendeeEmail) {
        return registrationQueryService.getRegistrationStatus(
                eventCode,
                attendeeEmail);
    }
}
