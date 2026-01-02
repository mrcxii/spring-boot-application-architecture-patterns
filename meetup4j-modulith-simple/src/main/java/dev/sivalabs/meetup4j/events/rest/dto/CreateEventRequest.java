package dev.sivalabs.meetup4j.events.rest.dto;

import dev.sivalabs.meetup4j.events.domain.EventType;
import dev.sivalabs.meetup4j.events.domain.InvalidEventCreationException;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateEventRequest(
        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
        String title,

        @NotBlank(message = "Description is required")
        @Size(max = 10000, message = "Description cannot exceed 10000 characters")
        String description,

        @NotNull(message = "Start datetime is required")
        Instant startDatetime,

        @NotNull(message = "End datetime is required")
        Instant endDatetime,

        @NotNull(message = "Event type is required")
        EventType type,

        @Size(max = 500, message = "Image URL cannot exceed 500 characters")
        @Pattern(regexp = "^https?://.*", message = "Image URL must be a valid HTTP/HTTPS URL")
        String imageUrl,

        @DecimalMin(value = "0.0", message = "Ticket price must be non-negative")
        BigDecimal ticketPrice,

        @Positive(message = "Capacity must be positive")
        @Max(value = 10000, message = "Capacity cannot exceed 10000")
        Integer capacity,

        String venue,

        String virtualLink) {

    public CreateEventRequest {
        if (type == EventType.OFFLINE && (venue == null || venue.isBlank())) {
            throw new InvalidEventCreationException("In-person events must have a venue");
        }
        if (type == EventType.ONLINE && (virtualLink == null || virtualLink.isBlank())) {
            throw new InvalidEventCreationException("Online events must have a virtual link");
        }
    }

    public BigDecimal ticketPrice() {
        return ticketPrice != null ? ticketPrice : BigDecimal.ZERO;
    }
}
