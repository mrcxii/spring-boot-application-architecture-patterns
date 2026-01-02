package dev.sivalabs.meetup4j.domain.models;

import java.util.List;

public record UserEventsVM(
        List<EventVM> upcomingEvents,
        List<EventVM> pastAttendedEvents) {}
