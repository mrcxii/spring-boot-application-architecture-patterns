package dev.sivalabs.meetup4j.events.application.query.dto;

import dev.sivalabs.meetup4j.events.domain.model.EventStatus;
import dev.sivalabs.meetup4j.events.domain.model.EventType;

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
