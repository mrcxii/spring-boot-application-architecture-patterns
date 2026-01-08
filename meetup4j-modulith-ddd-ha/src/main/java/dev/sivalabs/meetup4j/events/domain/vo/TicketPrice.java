package dev.sivalabs.meetup4j.events.domain.vo;

import dev.sivalabs.meetup4j.shared.AssertUtil;

import java.math.BigDecimal;

public record TicketPrice(BigDecimal amount) {
    public static final TicketPrice FREE = TicketPrice.of(BigDecimal.ZERO);

    public TicketPrice {
        AssertUtil.requireNotNull(amount, "Ticket price cannot be null");
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
