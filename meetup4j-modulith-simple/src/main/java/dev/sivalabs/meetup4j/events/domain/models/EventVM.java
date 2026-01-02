package dev.sivalabs.meetup4j.events.domain.models;

import dev.sivalabs.meetup4j.events.domain.EventStatus;
import dev.sivalabs.meetup4j.events.domain.EventType;

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
