package dev.sivalabs.meetup4j.events.rest;

import dev.sivalabs.meetup4j.events.domain.models.EventVM;

import java.util.List;

public record EventsResponse(List<EventVM> events) {}
