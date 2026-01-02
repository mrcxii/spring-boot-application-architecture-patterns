package dev.sivalabs.meetup4j.events.rest;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import dev.sivalabs.meetup4j.events.domain.EventType;
import dev.sivalabs.meetup4j.events.domain.vo.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreateEventRequest(
        @JsonUnwrapped
        @Valid
        EventDetails details,

        @JsonUnwrapped
        @Valid
        Schedule schedule,

        @NotNull(message = "Event type is required")
        EventType type,

        @Valid TicketPrice ticketPrice,

        @Valid Capacity capacity,

        @JsonUnwrapped
        @Valid
        EventLocation location) {
}
