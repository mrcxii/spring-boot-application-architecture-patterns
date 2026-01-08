package dev.sivalabs.meetup4j.events.interfaces.rest;

import dev.sivalabs.meetup4j.events.domain.model.EventType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;

record CreateEventRequest(
        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
        String title,

        @NotBlank(message = "Description is required")
        @Size(max = 10000, message = "Description cannot exceed 10000 characters")
        String description,

        @Size(max = 500, message = "Image URL cannot exceed 500 characters")
        @Pattern(regexp = "^https?://.*", message = "Image URL must be a valid HTTP/HTTPS URL")
        String imageUrl,

        @NotNull(message = "Start datetime is required")
        Instant startDatetime,

        @NotNull(message = "End datetime is required")
        Instant endDatetime,

        @NotNull(message = "Event type is required")
        EventType type,

        @DecimalMin(value = "0.0", message = "Ticket price must be non-negative")
        BigDecimal ticketPrice,

        @Positive(message = "Capacity must be positive")
        @Max(value = 10000, message = "Capacity cannot exceed 10000")
        Integer capacity,

        String venue,
        String virtualLink
) {
}
