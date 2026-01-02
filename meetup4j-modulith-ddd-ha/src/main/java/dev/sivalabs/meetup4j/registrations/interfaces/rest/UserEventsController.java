package dev.sivalabs.meetup4j.registrations.interfaces.rest;

import dev.sivalabs.meetup4j.registrations.application.query.RegistrationQueryService;
import dev.sivalabs.meetup4j.registrations.application.query.dto.UserEventsVM;
import dev.sivalabs.meetup4j.registrations.domain.vo.Email;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserEventsController {
    private final RegistrationQueryService registrationQueryService;

    UserEventsController(RegistrationQueryService registrationQueryService) {
        this.registrationQueryService = registrationQueryService;
    }

    @GetMapping("/api/user-events")
    UserEventsVM getMyEvents(@RequestParam String attendeeEmail) {
        return registrationQueryService.getAttendeeEvents(Email.of(attendeeEmail));
    }
}
