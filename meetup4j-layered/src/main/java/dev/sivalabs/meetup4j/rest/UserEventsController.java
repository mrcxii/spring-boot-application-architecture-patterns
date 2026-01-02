package dev.sivalabs.meetup4j.rest;

import dev.sivalabs.meetup4j.domain.models.UserEventsVM;
import dev.sivalabs.meetup4j.domain.services.EventRegistrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserEventsController {
    private final EventRegistrationService registrationService;

    UserEventsController(EventRegistrationService eventRegistrationService) {
        this.registrationService = eventRegistrationService;
    }

    @GetMapping("/api/user-events")
    UserEventsVM getMyEvents(@RequestParam String attendeeEmail) {
        return registrationService.getAttendeeEvents(attendeeEmail);
    }
}
