package dev.sivalabs.meetup4j.events.interfaces.rest;

import dev.sivalabs.meetup4j.events.application.query.dto.EventVM;

import java.util.List;

record EventsResponse(List<EventVM> events) {}
