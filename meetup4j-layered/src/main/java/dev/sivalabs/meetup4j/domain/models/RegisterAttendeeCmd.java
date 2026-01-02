package dev.sivalabs.meetup4j.domain.models;

public record RegisterAttendeeCmd(
        String eventCode,
        String attendeeName,
        String attendeeEmail) {}
