package dev.sivalabs.meetup4j.events.application.query;

import dev.sivalabs.meetup4j.events.application.query.dto.EventVM;
import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventQueryService {
    private final EventQueryRepository eventQueryRepository;

    EventQueryService(EventQueryRepository eventQueryRepository) {
        this.eventQueryRepository = eventQueryRepository;
    }

    public List<EventVM> getUpcomingEvents() {
        return eventQueryRepository.findUpcomingEvents();
    }

    public List<EventVM> findAllEvents() {
        return eventQueryRepository.findAll();
    }

    public EventVM getByCode(EventCode eventCode) {
        return eventQueryRepository.getEventViewByCode(eventCode);
    }

    public List<EventVM> getEventsByIds(List<EventId> eventIds) {
        return eventQueryRepository.findAllById(eventIds);
    }
}
