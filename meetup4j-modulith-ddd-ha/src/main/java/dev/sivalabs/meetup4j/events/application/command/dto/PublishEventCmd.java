package dev.sivalabs.meetup4j.events.application.command.dto;

import dev.sivalabs.meetup4j.events.domain.vo.EventCode;

public record PublishEventCmd(EventCode eventCode) {}
