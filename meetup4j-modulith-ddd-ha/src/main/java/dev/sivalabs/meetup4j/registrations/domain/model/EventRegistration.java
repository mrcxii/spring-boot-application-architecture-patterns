package dev.sivalabs.meetup4j.registrations.domain.model;

import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.registrations.domain.event.RegistrationAdded;
import dev.sivalabs.meetup4j.registrations.domain.event.RegistrationCancelled;
import dev.sivalabs.meetup4j.registrations.domain.vo.Email;
import dev.sivalabs.meetup4j.registrations.domain.vo.RegistrationCode;
import dev.sivalabs.meetup4j.registrations.domain.vo.RegistrationId;
import dev.sivalabs.meetup4j.shared.AggregateRoot;
import dev.sivalabs.meetup4j.shared.AssertUtil;

import java.time.Instant;

public class EventRegistration extends AggregateRoot {
    private RegistrationId id;
    private RegistrationCode code;
    private EventId eventId;
    private EventCode eventCode;
    private String attendeeName;
    private Email attendeeEmail;
    private Instant registeredAt;

    public EventRegistration(RegistrationId id, RegistrationCode code, EventId eventId,
                             EventCode eventCode, String attendeeName, Email attendeeEmail, Instant registeredAt) {
        this.id = id;
        this.code = AssertUtil.requireNotNull(code, "Registration code must not be null");
        this.eventId = AssertUtil.requireNotNull(eventId, "Event ID must not be null");
        this.eventCode = AssertUtil.requireNotNull(eventCode, "Event code must not be null");
        this.attendeeName = AssertUtil.requireNotNull(attendeeName, "Attendee name must not be null");
        this.attendeeEmail = AssertUtil.requireNotNull(attendeeEmail, "Attendee email must not be null");
        this.registeredAt = AssertUtil.requireNotNull(registeredAt, "Registered at must not be null");
    }

    public static EventRegistration create(EventId eventId, EventCode eventCode,
                                           String attendeeName, Email attendeeEmail) {
        var reg = new EventRegistration(
                RegistrationId.generate(),
                RegistrationCode.generate(),
                eventId,
                eventCode,
                attendeeName,
                attendeeEmail,
                Instant.now()
        );
        reg.register(new RegistrationAdded(eventId.id(), attendeeEmail.value()));
        return reg;
    }

    public void cancel() {
        register(new RegistrationCancelled(eventId.id(), attendeeEmail.value()));
    }

    public RegistrationId getId() {
        return id;
    }

    public RegistrationCode getCode() {
        return code;
    }

    public EventId getEventId() {
        return eventId;
    }

    public EventCode getEventCode() {
        return eventCode;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public Email getAttendeeEmail() {
        return attendeeEmail;
    }

    public Instant getRegisteredAt() {
        return registeredAt;
    }

}
