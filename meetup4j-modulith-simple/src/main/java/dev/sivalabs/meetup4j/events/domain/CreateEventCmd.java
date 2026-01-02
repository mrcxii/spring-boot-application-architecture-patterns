package dev.sivalabs.meetup4j.events.domain;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateEventCmd(
        String title,
        String description,
        Instant startDatetime,
        Instant endDatetime,
        EventType type,
        String imageUrl,
        BigDecimal ticketPrice,
        int capacity,
        String venue,
        String virtualLink) {
}
