package dev.sivalabs.meetup4j.domain.services;

import dev.sivalabs.meetup4j.domain.entities.EventRegistrationEntity;
import dev.sivalabs.meetup4j.domain.models.RegistrationVM;
import org.springframework.stereotype.Component;

@Component
class RegistrationMapper {

    public RegistrationVM toRegistrationVM(EventRegistrationEntity entity) {
        return new RegistrationVM(
                entity.getId(),
                entity.getCode(),
                entity.getEventId(),
                entity.getEventCode(),
                entity.getAttendeeName(),
                entity.getAttendeeEmail(),
                entity.getRegisteredAt());
    }

}
