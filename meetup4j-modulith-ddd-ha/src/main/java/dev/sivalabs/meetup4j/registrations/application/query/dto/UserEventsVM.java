package dev.sivalabs.meetup4j.registrations.application.query.dto;

import dev.sivalabs.meetup4j.events.application.query.dto.EventVM;

import java.util.List;

public record UserEventsVM(List<EventVM> upcomingEvents, List<EventVM> pastAttendedEvents) {}
