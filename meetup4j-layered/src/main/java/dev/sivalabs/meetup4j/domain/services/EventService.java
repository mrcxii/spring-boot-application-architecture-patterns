package dev.sivalabs.meetup4j.domain.services;

import dev.sivalabs.meetup4j.domain.entities.EventEntity;
import dev.sivalabs.meetup4j.domain.events.EventCancelled;
import dev.sivalabs.meetup4j.domain.events.EventCreated;
import dev.sivalabs.meetup4j.domain.events.EventPublished;
import dev.sivalabs.meetup4j.domain.exceptions.EventCancellationException;
import dev.sivalabs.meetup4j.domain.exceptions.EventSlotReservationException;
import dev.sivalabs.meetup4j.domain.models.*;
import dev.sivalabs.meetup4j.domain.repositories.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final SpringEventPublisher eventPublisher;

    EventService(EventRepository eventRepository, EventMapper eventMapper, SpringEventPublisher eventPublisher) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.eventPublisher = eventPublisher;
    }

    public List<EventVM> getUpcomingEvents() {
        return eventRepository.findUpcomingEvents(Instant.now())
                .stream().map(eventMapper::toEventVM).toList();
    }

    public List<EventVM> findAllEvents() {
        return eventRepository.findAll()
                .stream().map(eventMapper::toEventVM).toList();
    }

    public EventVM getByCode(String eventCode) {
        var event = eventRepository.getByCode(eventCode);
        return eventMapper.toEventVM(event);
    }

    public List<EventVM> getEventsByIds(List<Long> eventIds) {
        return eventRepository.findAllById(eventIds)
                .stream().map(eventMapper::toEventVM).toList();
    }

    @Transactional
    public String createEvent(CreateEventCmd cmd) {
        var event = new EventEntity();
        event.setCode(UUID.randomUUID().toString());
        event.setTitle(cmd.title());
        event.setDescription(cmd.description());
        event.setStartDatetime(cmd.startDatetime());
        event.setEndDatetime(cmd.endDatetime());
        event.setVenue(cmd.venue());
        event.setVirtualLink(cmd.virtualLink());
        event.setImageUrl(cmd.imageUrl());
        event.setType(cmd.type());
        event.setTicketPrice(cmd.ticketPrice());
        event.setCapacity(cmd.capacity());
        event.setStatus(EventStatus.DRAFT);

        eventRepository.save(event);
        eventPublisher.publish(new EventCreated(event.getCode(), event.getTitle(), event.getDescription()));
        return event.getCode();
    }

    @Transactional
    public void cancelEvent(CancelEventCmd cmd) {
        EventEntity event = eventRepository.getByCode(cmd.eventCode());
        if(event.getStartDatetime().isBefore(Instant.now())) {
            throw new EventCancellationException("Event has already started, cannot be cancelled");
        }
        if(event.getStatus() == EventStatus.CANCELLED) {
            return;
        }
        event.setStatus(EventStatus.CANCELLED);
        eventRepository.save(event);
        eventPublisher.publish(new EventCancelled(event.getCode(), event.getTitle(), event.getDescription()));
    }

    @Transactional
    public void publishEvent(PublishEventCmd cmd) {
        EventEntity event = eventRepository.getByCode(cmd.eventCode());
        if(event.getStatus() == EventStatus.PUBLISHED) {
            return;
        }
        event.setStatus(EventStatus.PUBLISHED);
        eventRepository.save(event);
        eventPublisher.publish(new EventPublished(event.getCode(), event.getTitle(), event.getDescription()));
    }

    @Transactional
    public void reserveSlotForEvent(Long eventId) {
        var event = eventRepository.getEventById(eventId);
        if (event.getStatus() != EventStatus.PUBLISHED) {
            throw new EventSlotReservationException("This event is not open for registration");
        }

        if (event.getStartDatetime().isBefore(Instant.now())) {
            throw new EventSlotReservationException("Cannot register for past events");
        }

        if (!event.hasFreeSeats()) {
            throw new EventSlotReservationException("Event is full");
        }
        event.setRegistrationsCount(event.getRegistrationsCount() + 1);
        eventRepository.save(event);
    }

    @Transactional
    public void freeSlotForEvent(Long eventId) {
        var event = eventRepository.getEventById(eventId);
        event.setRegistrationsCount(event.getRegistrationsCount() - 1);
        eventRepository.save(event);
    }
}
