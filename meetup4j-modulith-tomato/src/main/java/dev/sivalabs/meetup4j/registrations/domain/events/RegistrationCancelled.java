package dev.sivalabs.meetup4j.registrations.domain.events;

public record RegistrationCancelled(Long eventId, String attendeeEmail) {}
