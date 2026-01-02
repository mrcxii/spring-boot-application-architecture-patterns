package dev.sivalabs.meetup4j.events.infra.persistence;

import dev.sivalabs.meetup4j.events.application.query.EventQueryRepository;
import dev.sivalabs.meetup4j.events.application.query.dto.EventVM;
import dev.sivalabs.meetup4j.events.domain.model.Event;
import dev.sivalabs.meetup4j.events.domain.repository.EventRepository;
import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
class EventRepositoryAdapter implements EventRepository, EventQueryRepository {
    private final JpaEventRepository jpaEventRepository;
    private final EventEntityMapper eventEntityMapper;
    private final EventViewMapper eventViewMapper;

    EventRepositoryAdapter(JpaEventRepository jpaEventRepository, EventEntityMapper eventEntityMapper, EventViewMapper eventViewMapper) {
        this.jpaEventRepository = jpaEventRepository;
        this.eventEntityMapper = eventEntityMapper;
        this.eventViewMapper = eventViewMapper;
    }

    @Override
    public void create(Event event) {
        EventEntity entity = EventEntity.createDraft(
                event.getDetails(),
                event.getSchedule(),
                event.getType(),
                event.getTicketPrice(),
                event.getCapacity(),
                event.getLocation()
        );
        jpaEventRepository.save(entity);
    }

    @Override
    public void update(Event event) {
        var entity = jpaEventRepository.getEventById(event.getId());
        entity.updateStatus(event.getStatus())
              .updateRegistrationsCount(event.getRegistrationsCount());
        jpaEventRepository.save(entity);
    }

    @Override
    public Optional<Event> findById(EventId eventId) {
        return jpaEventRepository.findById(eventId)
                .map(eventEntityMapper::toEvent);
    }

    @Override
    public Optional<Event> findByCode(EventCode code) {
        return jpaEventRepository.findByCode(code)
                .map(eventEntityMapper::toEvent);
    }

    @Override
    public List<EventVM> findUpcomingEvents() {
        return jpaEventRepository.findUpcomingEvents(Instant.now())
                .stream().map(eventViewMapper::toEventVM).toList();
    }

    @Override
    public List<EventVM> findAll() {
        return jpaEventRepository.findAll()
                .stream().map(eventViewMapper::toEventVM).toList();
    }

    @Override
    public List<EventVM> findAllById(List<EventId> eventIds) {
        return jpaEventRepository.findAllById(eventIds)
                .stream().map(eventViewMapper::toEventVM).toList();
    }

    @Override
    public EventVM getEventViewByCode(EventCode code) {
        EventEntity event = jpaEventRepository.getByCode(code);
        return eventViewMapper.toEventVM(event);
    }
}
