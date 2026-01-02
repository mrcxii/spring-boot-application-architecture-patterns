package dev.sivalabs.meetup4j.events.domain;

import dev.sivalabs.meetup4j.events.domain.models.EventVM;
import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.shared.SpringEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventQueryService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final SpringEventPublisher eventPublisher;

    EventQueryService(EventRepository eventRepository, EventMapper eventMapper, SpringEventPublisher eventPublisher) {
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

    public EventVM getByCode(EventCode eventCode) {
        var event = eventRepository.getByCode(eventCode);
        return eventMapper.toEventVM(event);
    }

    public List<EventVM> getEventsByIds(List<EventId> eventIds) {
        return eventRepository.findAllById(eventIds)
                .stream().map(eventMapper::toEventVM).toList();
    }
}
