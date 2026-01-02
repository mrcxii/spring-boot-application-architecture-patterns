package dev.sivalabs.meetup4j.events.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record TicketPrice(
        @JsonValue
        @DecimalMin(value = "0.0", message = "Ticket price must be non-negative")
        BigDecimal amount) {
    public static final TicketPrice FREE = TicketPrice.of(BigDecimal.ZERO);

    @JsonCreator
    public TicketPrice {
        if (amount == null) {
            throw new IllegalArgumentException("Ticket price cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Ticket price cannot be negative");
        }
    }

    public static TicketPrice of(BigDecimal amount) {
        if (amount == null) {
            return FREE;
        }
        return new TicketPrice(amount);
    }
}
