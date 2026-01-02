package dev.sivalabs.meetup4j.events.rest.dto;

import dev.sivalabs.meetup4j.events.domain.models.EventVM;

import java.util.List;

public record EventsResponse(List<EventVM> events) {}
