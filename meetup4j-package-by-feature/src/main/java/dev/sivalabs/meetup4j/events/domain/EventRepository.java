package dev.sivalabs.meetup4j.events.domain;

import dev.sivalabs.meetup4j.shared.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

interface EventRepository extends JpaRepository<EventEntity, Long> {

    @Query("""
            SELECT e FROM EventEntity e
            WHERE e.status = dev.sivalabs.meetup4j.events.domain.EventStatus.PUBLISHED
            AND e.startDatetime > :now
            ORDER BY e.startDatetime ASC
            """)
    List<EventEntity> findUpcomingEvents(@Param("now") Instant now);

    @Query("""
            SELECT e FROM EventEntity e
            WHERE e.code = :code
            """)
    Optional<EventEntity> findByCode(@Param("code") String code);

    // -------- convenience methods ------------
    default EventEntity getEventById(Long eventId) {
        return this.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));
    }

    default EventEntity getByCode(String eventCode) {
        return this.findByCode(eventCode)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with code: " + eventCode));
    }
}
