package dev.sivalabs.meetup4j.rest.dto;

import dev.sivalabs.meetup4j.domain.models.EventVM;

import java.util.List;

public record EventsResponse(List<EventVM> events) {}
