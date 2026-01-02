package dev.sivalabs.meetup4j.domain.repositories;

import dev.sivalabs.meetup4j.domain.entities.EventRegistrationEntity;
import dev.sivalabs.meetup4j.domain.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<EventRegistrationEntity, Long> {

    Optional<EventRegistrationEntity> findByEventCodeAndAttendeeEmail(String eventCode, String attendeeEmail);

    List<EventRegistrationEntity> findByEventIdOrderByRegisteredAtDesc(Long eventId);

    @Query("""
            SELECT r.attendeeName
            FROM EventRegistrationEntity r
            WHERE r.eventCode = :eventCode order by r.attendeeName
            """)
    List<String> findAttendeeNamesByEventCode(String eventCode);

    @Query("""
    SELECT er.eventId FROM EventRegistrationEntity er
    JOIN EventEntity e ON e.id = er.eventId
    WHERE er.attendeeEmail = :attendeeEmail
    AND e.startDatetime > CURRENT_TIMESTAMP
    ORDER BY e.startDatetime
    """)
    List<Long> findUpcomingEventIdsByAttendeeEmail(@Param("attendeeEmail") String attendeeEmail);

    @Query("""
    SELECT er.eventId FROM EventRegistrationEntity er
    JOIN EventEntity e ON e.id = er.eventId
    WHERE er.attendeeEmail = :attendeeEmail
    AND e.endDatetime < CURRENT_TIMESTAMP
    ORDER BY e.startDatetime
    """)
    List<Long> findPastAttendedEventIdsByAttendeeEmail(@Param("attendeeEmail") String attendeeEmail);

    Optional<EventRegistrationEntity> findByCode(String code);

    // -------- convenience methods ------------

    default EventRegistrationEntity getByCode(String code) {
        return this.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found with code: " + code));
    }
}
