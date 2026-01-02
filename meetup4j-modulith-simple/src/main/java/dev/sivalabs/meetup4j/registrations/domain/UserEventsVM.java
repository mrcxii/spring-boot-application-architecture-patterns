package dev.sivalabs.meetup4j.registrations.domain;

import dev.sivalabs.meetup4j.events.domain.models.EventVM;

import java.util.List;

public record UserEventsVM(
        List<EventVM> upcomingEvents,
        List<EventVM> pastAttendedEvents) {}
