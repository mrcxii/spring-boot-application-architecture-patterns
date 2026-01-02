package dev.sivalabs.meetup4j.registrations.application.command.dto;

import dev.sivalabs.meetup4j.registrations.domain.vo.RegistrationCode;

public record CancelRegistrationCmd(RegistrationCode registrationCode) {}
