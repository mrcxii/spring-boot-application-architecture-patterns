package dev.sivalabs.meetup4j.events.interfaces.rest;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import dev.sivalabs.meetup4j.events.domain.model.EventType;
import dev.sivalabs.meetup4j.events.domain.vo.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

record CreateEventRequest(
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
