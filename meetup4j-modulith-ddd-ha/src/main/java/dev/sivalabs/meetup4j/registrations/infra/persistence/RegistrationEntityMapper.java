package dev.sivalabs.meetup4j.registrations.infra.persistence;

import dev.sivalabs.meetup4j.registrations.domain.model.EventRegistration;
import org.springframework.stereotype.Component;

@Component
class RegistrationEntityMapper {

    public EventRegistration toDomain(EventRegistrationEntity entity) {
        return new EventRegistration(
                entity.getId(),
                entity.getCode(),
                entity.getEventId(),
                entity.getEventCode(),
                entity.getAttendeeName(),
                entity.getAttendeeEmail(),
                entity.getRegisteredAt());
    }

    public EventRegistrationEntity toEntity(EventRegistration registration) {
        return new EventRegistrationEntity(
                registration.getId(),
                registration.getCode(),
                registration.getEventId(),
                registration.getEventCode(),
                registration.getAttendeeName(),
                registration.getAttendeeEmail(),
                registration.getRegisteredAt());
    }
}
