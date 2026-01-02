package dev.sivalabs.meetup4j.registrations.domain;

import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.registrations.domain.vo.Email;
import dev.sivalabs.meetup4j.registrations.domain.vo.RegistrationCode;
import dev.sivalabs.meetup4j.shared.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<EventRegistrationEntity, Long> {

    Optional<EventRegistrationEntity> findByEventCodeAndAttendeeEmail(EventCode eventCode, Email attendeeEmail);

    List<EventRegistrationEntity> findByEventIdOrderByRegisteredAtDesc(EventId eventId);

    @Query("""
            SELECT r.attendeeName
            FROM EventRegistrationEntity r
            WHERE r.eventCode = :eventCode order by r.attendeeName
            """)
    List<String> findAttendeeNamesByEventCode(EventCode eventCode);

    @Query("""
    SELECT er.eventId FROM EventRegistrationEntity er
    JOIN EventEntity e ON e.id = er.eventId
    WHERE er.attendeeEmail = :attendeeEmail
    AND e.schedule.startDatetime > CURRENT_TIMESTAMP
    ORDER BY e.schedule.startDatetime
    """)
    List<EventId> findUpcomingEventIdsByAttendeeEmail(@Param("attendeeEmail") Email attendeeEmail);

    @Query("""
    SELECT er.eventId FROM EventRegistrationEntity er
    JOIN EventEntity e ON e.id = er.eventId
    WHERE er.attendeeEmail = :attendeeEmail
    AND e.schedule.endDatetime < CURRENT_TIMESTAMP
    ORDER BY e.schedule.startDatetime
    """)
    List<EventId> findPastAttendedEventIdsByAttendeeEmail(@Param("attendeeEmail") Email attendeeEmail);

    Optional<EventRegistrationEntity> findByCode(RegistrationCode code);

    // -------- convenience methods ------------

    default EventRegistrationEntity getByCode(RegistrationCode code) {
        return this.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found with code: " + code));
    }
}
