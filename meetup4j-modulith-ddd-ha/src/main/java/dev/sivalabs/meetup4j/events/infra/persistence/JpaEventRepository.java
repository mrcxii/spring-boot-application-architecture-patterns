package dev.sivalabs.meetup4j.events.infra.persistence;

import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.shared.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

interface JpaEventRepository extends JpaRepository<EventEntity, EventId> {

    @Query("""
            SELECT e FROM EventEntity e
            WHERE e.status = dev.sivalabs.meetup4j.events.domain.model.EventStatus.PUBLISHED
            AND e.schedule.startDatetime > :now
            ORDER BY e.schedule.startDatetime ASC
            """)
    List<EventEntity> findUpcomingEvents(@Param("now") Instant now);

    @Query("""
            SELECT e FROM EventEntity e
            WHERE e.code = :code
            """)
    Optional<EventEntity> findByCode(@Param("code") EventCode code);

    // -------- convenience methods ------------
    default EventEntity getEventById(EventId eventId) {
        return this.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
    }

    default EventEntity getByCode(EventCode eventCode) {
        return this.findByCode(eventCode)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with code: " + eventCode));
    }
}
