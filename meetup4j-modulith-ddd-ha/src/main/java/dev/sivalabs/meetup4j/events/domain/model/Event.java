package dev.sivalabs.meetup4j.events.domain.model;

import dev.sivalabs.meetup4j.events.domain.event.EventCancelled;
import dev.sivalabs.meetup4j.events.domain.event.EventCreated;
import dev.sivalabs.meetup4j.events.domain.event.EventPublished;
import dev.sivalabs.meetup4j.events.domain.exception.EventCancellationException;
import dev.sivalabs.meetup4j.events.domain.vo.*;
import dev.sivalabs.meetup4j.shared.AggregateRoot;

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
        this.id = id;
        this.code = code;
        this.details = details;
        this.schedule = schedule;
        this.type = type;
        this.status = eventStatus;
        this.ticketPrice = ticketPrice;
        this.capacity = capacity;
        this.location = location;
        this.registrationsCount = registrationsCount;
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

    public Event updateRegistrationsCount(int registrationsCount) {
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
