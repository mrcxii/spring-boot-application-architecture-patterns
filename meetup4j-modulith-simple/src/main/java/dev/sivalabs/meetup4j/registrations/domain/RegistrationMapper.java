package dev.sivalabs.meetup4j.registrations.domain;

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
