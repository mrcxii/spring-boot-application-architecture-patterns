package dev.sivalabs.meetup4j.registrations.interfaces.rest;

import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.registrations.domain.vo.Email;
import jakarta.validation.constraints.NotNull;

public record EventRegistrationRequest(
        @NotNull(message = "Event code is required") EventCode eventCode,
        @NotNull(message = "Attendee name is required") String attendeeName,
        @NotNull(message = "Attendee email is required") Email attendeeEmail
) {}
