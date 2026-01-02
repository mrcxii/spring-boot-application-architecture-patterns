package dev.sivalabs.meetup4j.registrations.domain;

public record RegisterAttendeeCmd(
        String eventCode,
        String attendeeName,
        String attendeeEmail) {}
