package dev.sivalabs.meetup4j.events.domain.model;

import dev.sivalabs.meetup4j.events.domain.event.EventCancelled;
import dev.sivalabs.meetup4j.events.domain.event.EventCreated;
import dev.sivalabs.meetup4j.events.domain.event.EventPublished;
import dev.sivalabs.meetup4j.events.domain.exception.EventCancellationException;
import dev.sivalabs.meetup4j.events.domain.exception.EventSlotReservationException;
import dev.sivalabs.meetup4j.events.domain.vo.*;
import dev.sivalabs.meetup4j.shared.AggregateRoot;
import dev.sivalabs.meetup4j.shared.AssertUtil;

import java.time.Instant;

public class Event extends AggregateRoot {
    private EventId id;
    private EventCode code;
    private EventDetails details;
    private Schedule schedule;
    private EventType type;
    private EventStatus status;
    private TicketPrice ticketPrice;
    private Capacity capacity;
    private EventLocation location;
    private int registrationsCount;

    public Event(EventId id,
                 EventCode code,
                 EventDetails details,
                 Schedule schedule,
                 EventType type,
                 EventStatus eventStatus,
                 TicketPrice ticketPrice,
                 Capacity capacity,
                 EventLocation location,
                 int registrationsCount) {
        this.id = AssertUtil.requireNotNull(id, "Event ID cannot be null");
        this.code = AssertUtil.requireNotNull(code, "Event code cannot be null");
        this.details = AssertUtil.requireNotNull(details, "Event details cannot be null");
        this.schedule = AssertUtil.requireNotNull(schedule, "Event schedule cannot be null");
        this.type = AssertUtil.requireNotNull(type, "Event type cannot be null");
        this.status = AssertUtil.requireNotNull(eventStatus, "Event status cannot be null");
        this.ticketPrice = AssertUtil.requireNotNull(ticketPrice, "Event ticket price cannot be null");
        this.capacity = AssertUtil.requireNotNull(capacity, "Event capacity cannot be null");
        this.location = AssertUtil.requireNotNull(location, "Event location cannot be null");
        this.registrationsCount = AssertUtil.requireMin(registrationsCount, 0, "Event registrations count cannot be negative");
    }

    public static Event createDraft(
            EventDetails details,
            Schedule schedule,
            EventType type,
            TicketPrice ticketPrice,
            Capacity capacity,
            EventLocation location) {

        var event = new Event(
                EventId.generate(),
                EventCode.generate(),
                details,
                schedule,
                type,
                EventStatus.DRAFT,
                ticketPrice,
                capacity,
                location,
                0);

        event.register(new EventCreated(event.getCode().code(), event.getDetails().title(), event.getDetails().description()));
        return event;
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
        this.register(new EventPublished(this.getCode().code(), this.getDetails().title(), this.getDetails().description()));

        return true;
    }

    public boolean cancel() {
        if (this.isStarted()) {
            throw new EventCancellationException("Cannot modify events that have already started");
        }
        if (this.isCancelled()) {
            return false;
        }
        this.status = EventStatus.CANCELLED;
        this.register(new EventCancelled(this.getCode().code(), this.getDetails().title(), this.getDetails().description()));
        return true;
    }

    public Event reserveSlot() {
        if (this.getStatus() != EventStatus.PUBLISHED) {
            throw new EventSlotReservationException("This event is not open for registration");
        }
        if (this.isStarted()) {
            throw new EventSlotReservationException("Cannot register for past events");
        }
        if (!this.hasFreeSeats()) {
            throw new EventSlotReservationException("Event is full");
        }
        this.registrationsCount = this.registrationsCount + 1;
        return this;
    }

    public Event freeSlot() {
        this.registrationsCount = this.registrationsCount - 1;
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
