package dev.sivalabs.meetup4j.events.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public record Schedule(
        @NotNull(message = "Start datetime is required")
        Instant startDatetime,
        @NotNull(message = "End datetime is required")
        Instant endDatetime) {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Schedule(
            @JsonProperty("startDatetime") Instant startDatetime,
            @JsonProperty("endDatetime") Instant endDatetime
    ) {
        if (startDatetime == null || endDatetime == null) {
            throw new IllegalArgumentException("Start and end datetime cannot be null");
        }
        if (endDatetime.isBefore(startDatetime)) {
            throw new IllegalArgumentException("End datetime cannot be before start datetime");
        }
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
    }

    public static Schedule of(Instant startDatetime, Instant endDatetime) {
        return new Schedule(startDatetime, endDatetime);
    }
}
