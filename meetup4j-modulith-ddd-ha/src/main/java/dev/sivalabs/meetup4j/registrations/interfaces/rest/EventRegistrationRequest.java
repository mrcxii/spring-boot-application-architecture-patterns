package dev.sivalabs.meetup4j.registrations.interfaces.rest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EventRegistrationRequest(
        @NotBlank(message = "Event code is required")
        String eventCode,
        @NotBlank(message = "Attendee name is required")
        String attendeeName,
        @NotBlank(message = "Attendee email is required")
        @Email(message = "Invalid attendee email format")
        String attendeeEmail
) {}
