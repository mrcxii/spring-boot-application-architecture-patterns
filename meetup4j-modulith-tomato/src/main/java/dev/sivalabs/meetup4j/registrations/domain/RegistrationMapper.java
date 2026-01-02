package dev.sivalabs.meetup4j.registrations.domain;

import org.springframework.stereotype.Component;

@Component
public class RegistrationMapper {

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
