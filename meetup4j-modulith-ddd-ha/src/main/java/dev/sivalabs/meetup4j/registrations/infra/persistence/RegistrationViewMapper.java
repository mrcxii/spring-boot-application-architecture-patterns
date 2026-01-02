package dev.sivalabs.meetup4j.registrations.infra.persistence;

import dev.sivalabs.meetup4j.registrations.application.query.dto.RegistrationVM;
import org.springframework.stereotype.Component;

@Component
class RegistrationViewMapper {

    public RegistrationVM toRegistrationVM(EventRegistrationEntity entity) {
        return new RegistrationVM(
                entity.getId().id(),
                entity.getCode().code(),
                entity.getEventId().id(),
                entity.getEventCode().code(),
                entity.getAttendeeName(),
                entity.getAttendeeEmail().value(),
                entity.getRegisteredAt());
    }

}
