package dev.sivalabs.meetup4j.registrations.domain;

import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.registrations.domain.vo.Email;
import dev.sivalabs.meetup4j.registrations.domain.vo.RegistrationCode;
import dev.sivalabs.meetup4j.registrations.domain.vo.RegistrationId;
import dev.sivalabs.meetup4j.shared.AssertUtil;
import dev.sivalabs.meetup4j.shared.BaseEntity;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "event_registrations")
public class EventRegistrationEntity extends BaseEntity {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "id", nullable = false))
    private RegistrationId id;

    @Embedded
    @AttributeOverride(name = "code", column = @Column(name = "code", nullable = false, unique = true))
    private RegistrationCode code;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "event_id", nullable = false))
    private EventId eventId;

    @Embedded
    @AttributeOverride(name = "code", column = @Column(name = "event_code", nullable = false))
    private EventCode eventCode;

    @Column(name = "attendee_name", nullable = false)
    private String attendeeName;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "attendee_email", nullable = false))
    private Email attendeeEmail;

    @Column(name = "registered_at", nullable = false)
    private Instant registeredAt;

    @Version
    private int version;

    protected EventRegistrationEntity() {}

    public EventRegistrationEntity(RegistrationId id, RegistrationCode code, EventId eventId,
                                   EventCode eventCode, String attendeeName, Email attendeeEmail, Instant registeredAt) {
        this.id = id;
        this.code = AssertUtil.requireNotNull(code, "Registration code must not be null");
        this.eventId = AssertUtil.requireNotNull(eventId, "Event ID must not be null");
        this.eventCode = AssertUtil.requireNotNull(eventCode, "Event code must not be null");
        this.attendeeName = AssertUtil.requireNotNull(attendeeName, "Attendee name must not be null");
        this.attendeeEmail = AssertUtil.requireNotNull(attendeeEmail, "Attendee email must not be null");
        this.registeredAt = AssertUtil.requireNotNull(registeredAt, "Registered at must not be null");
    }

    public static EventRegistrationEntity create(EventId eventId, EventCode eventCode,
                                             String attendeeName, Email attendeeEmail) {
        return new EventRegistrationEntity(
                RegistrationId.generate(),
                RegistrationCode.generate(),
                eventId,
                eventCode,
                attendeeName,
                attendeeEmail,
                Instant.now()
        );
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
