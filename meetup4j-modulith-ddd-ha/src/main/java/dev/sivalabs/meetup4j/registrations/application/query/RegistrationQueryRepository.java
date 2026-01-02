package dev.sivalabs.meetup4j.registrations.application.query;

import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.registrations.application.query.dto.RegistrationVM;
import dev.sivalabs.meetup4j.registrations.application.query.dto.UserEventsVM;
import dev.sivalabs.meetup4j.registrations.domain.vo.Email;

import java.util.List;
import java.util.Optional;

public interface RegistrationQueryRepository {
    List<RegistrationVM> findByEventId(EventId eventId);

    List<String> findEventAttendeeNames(EventCode eventCode);

    UserEventsVM findEvents(Email attendeeEmail);

    Optional<RegistrationVM> findAttendeeRegistration(EventCode eventCode, Email attendeeEmail);
}
