package dev.sivalabs.meetup4j.events.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

public record Capacity(
        @JsonValue
        @Positive(message = "Capacity must be positive")
        @Max(value = 10000, message = "Capacity cannot exceed 10000")
        Integer value) {
    public static final Capacity UNLIMITED = new Capacity(null);

    @JsonCreator
    public Capacity {
        if (value != null && value < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
    }

    public static Capacity of(Integer capacity) {
        if (capacity == null) {
            return UNLIMITED;
        }
        return new Capacity(capacity);
    }
}
