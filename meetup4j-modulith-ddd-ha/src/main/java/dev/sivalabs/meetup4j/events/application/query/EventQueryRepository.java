package dev.sivalabs.meetup4j.events.application.query;

import dev.sivalabs.meetup4j.events.application.query.dto.EventVM;
import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;

import java.util.List;

public interface EventQueryRepository {
    List<EventVM> findUpcomingEvents();

    List<EventVM> findAll();

    List<EventVM> findAllById(List<EventId> eventIds);

    EventVM getEventViewByCode(EventCode code);
}
