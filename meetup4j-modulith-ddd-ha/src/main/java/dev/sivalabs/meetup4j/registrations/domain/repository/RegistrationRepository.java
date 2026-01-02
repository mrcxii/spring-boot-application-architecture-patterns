package dev.sivalabs.meetup4j.registrations.domain.repository;

import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.registrations.domain.model.EventRegistration;
import dev.sivalabs.meetup4j.registrations.domain.vo.Email;
import dev.sivalabs.meetup4j.registrations.domain.vo.RegistrationCode;
import dev.sivalabs.meetup4j.shared.exception.ResourceNotFoundException;

import java.util.Optional;

public interface RegistrationRepository {

    Optional<EventRegistration> findRegistration(EventCode eventCode, Email attendeeEmail);

    Optional<EventRegistration> findByCode(RegistrationCode code);

    void create(EventRegistration registration);

    void delete(EventRegistration registration);

    // -------- convenience methods ------------

    default EventRegistration getByCode(RegistrationCode code) {
        return this.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found with code: " + code));
    }
}
