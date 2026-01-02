package dev.sivalabs.meetup4j.registrations.domain;

import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.registrations.domain.vo.Email;

public record RegisterAttendeeCmd(EventCode eventCode, String attendeeName, Email attendeeEmail) {}
