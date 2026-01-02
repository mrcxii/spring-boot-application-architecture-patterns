package dev.sivalabs.meetup4j.registrations.rest.dto;

import jakarta.validation.constraints.NotNull;

public record EventRegistrationRequest(
        @NotNull(message = "Event code is required") String eventCode,
        @NotNull(message = "Attendee name is required") String attendeeName,
        @NotNull(message = "Attendee email is required") String attendeeEmail
) {}
