package dev.sivalabs.meetup4j.domain.models;

import java.math.BigDecimal;
import java.time.Instant;

public record EventVM(
        Long id,
        String code,
        String title,
        String description,
        Instant startDatetime,
        Instant endDatetime,
        EventType type,
        EventStatus status,
        String imageUrl,
        BigDecimal ticketPrice,
        Integer capacity,
        String venue,
        String virtualLink,
        int registeredUsersCount) {}
