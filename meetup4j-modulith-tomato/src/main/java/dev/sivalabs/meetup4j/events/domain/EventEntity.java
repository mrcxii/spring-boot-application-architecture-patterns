package dev.sivalabs.meetup4j.events.domain;

import dev.sivalabs.meetup4j.events.domain.vo.*;
import dev.sivalabs.meetup4j.shared.BaseEntity;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "events")
class EventEntity extends BaseEntity {

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "id", nullable = false))
    private EventId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "code", nullable = false, unique = true))
    private EventCode code;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "title", column = @Column(name = "title", nullable = false)),
        @AttributeOverride(name = "description", column = @Column(name = "description")),
        @AttributeOverride(name = "imageUrl", column = @Column(name = "image_url"))
    })
    private EventDetails details;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "startDatetime", column = @Column(name = "start_datetime", nullable = false)),
        @AttributeOverride(name = "endDatetime", column = @Column(name = "end_datetime", nullable = false))
    })
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EventStatus status;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "ticket_price"))
    private TicketPrice ticketPrice = TicketPrice.FREE;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "capacity"))
    private Capacity capacity;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "venue", column = @Column(name = "venue")),
            @AttributeOverride(name = "virtualLink", column = @Column(name = "virtual_link"))
    })
    private EventLocation location;

    @Column(name = "registrations_count", nullable = false)
    private int registrationsCount;

    @Version
    private int version;

    protected EventEntity() {}

    public EventEntity(EventId id,
                       EventCode code,
                       EventDetails details,
                       Schedule schedule,
                       EventType type,
                       EventStatus eventStatus,
                       TicketPrice ticketPrice,
                       Capacity capacity,
                       EventLocation location) {
        this.id = id;
        this.code = code;
        this.details = details;
        this.schedule = schedule;
        this.type = type;
        this.status = eventStatus;
        this.ticketPrice = ticketPrice;
        this.capacity = capacity;
        this.location = location;
    }

    public static EventEntity createDraft(
            EventDetails details,
            Schedule schedule,
            EventType type,
            TicketPrice ticketPrice,
            Capacity capacity,
            EventLocation location) {

        return new EventEntity(
                EventId.generate(),
                EventCode.generate(),
                details,
                schedule,
                type,
                EventStatus.DRAFT,
                ticketPrice,
                capacity,
                location);
    }

    public boolean hasFreeSeats() {
        return capacity == null || capacity.value() == null || capacity.value() > registrationsCount;
    }

    public boolean isPublished() {
        return status == EventStatus.PUBLISHED;
    }

    public boolean isCancelled() {
        return status == EventStatus.CANCELLED;
    }

    public boolean isStarted() {
        return schedule.startDatetime().isBefore(Instant.now());
    }

    public boolean publish() {
        if (this.isPublished()) {
            return false;
        }
        this.status = EventStatus.PUBLISHED;
        return true;
    }

    public boolean cancel() {
        if (this.isStarted()) {
            throw new EventCancellationException("Cannot cancel events that have already started");
        }
        if (this.isCancelled()) {
            return false;
        }
        this.status = EventStatus.CANCELLED;
        return true;
    }

    public EventEntity updateRegistrationsCount(int registrationsCount) {
        this.registrationsCount = registrationsCount;
        return this;
    }

    public EventId getId() {
        return id;
    }

    public EventCode getCode() {
        return code;
    }

    public EventDetails getDetails() {
        return details;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public EventType getType() {
        return type;
    }

    public EventStatus getStatus() {
        return status;
    }

    public TicketPrice getTicketPrice() {
        return ticketPrice;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public EventLocation getLocation() {
        return location;
    }

    public int getRegistrationsCount() {
        return registrationsCount;
    }
}
