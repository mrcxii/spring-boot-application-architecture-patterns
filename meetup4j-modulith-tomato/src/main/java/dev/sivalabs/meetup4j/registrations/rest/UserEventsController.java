package dev.sivalabs.meetup4j.registrations.rest;

import dev.sivalabs.meetup4j.registrations.domain.EventRegistrationQueryService;
import dev.sivalabs.meetup4j.registrations.domain.UserEventsVM;
import dev.sivalabs.meetup4j.registrations.domain.vo.Email;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserEventsController {
    private final EventRegistrationQueryService eventRegistrationQueryService;

    UserEventsController(EventRegistrationQueryService eventRegistrationQueryService) {
        this.eventRegistrationQueryService = eventRegistrationQueryService;
    }

    @GetMapping("/api/user-events")
    UserEventsVM getMyEvents(@RequestParam Email attendeeEmail) {
        return eventRegistrationQueryService.getAttendeeEvents(attendeeEmail);
    }
}
