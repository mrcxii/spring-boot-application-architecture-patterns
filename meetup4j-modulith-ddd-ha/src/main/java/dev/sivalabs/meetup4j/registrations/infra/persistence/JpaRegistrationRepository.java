package dev.sivalabs.meetup4j.registrations.infra.persistence;

import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.registrations.domain.vo.Email;
import dev.sivalabs.meetup4j.registrations.domain.vo.RegistrationCode;
import dev.sivalabs.meetup4j.registrations.domain.vo.RegistrationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

interface JpaRegistrationRepository extends JpaRepository<EventRegistrationEntity, RegistrationId> {

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
}
