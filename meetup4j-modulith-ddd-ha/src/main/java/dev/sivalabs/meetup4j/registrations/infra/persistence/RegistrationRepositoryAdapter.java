package dev.sivalabs.meetup4j.registrations.infra.persistence;

import dev.sivalabs.meetup4j.events.application.EventsAPI;
import dev.sivalabs.meetup4j.events.application.query.dto.EventVM;
import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.registrations.application.query.RegistrationQueryRepository;
import dev.sivalabs.meetup4j.registrations.application.query.dto.RegistrationVM;
import dev.sivalabs.meetup4j.registrations.application.query.dto.UserEventsVM;
import dev.sivalabs.meetup4j.registrations.domain.model.EventRegistration;
import dev.sivalabs.meetup4j.registrations.domain.repository.RegistrationRepository;
import dev.sivalabs.meetup4j.registrations.domain.vo.Email;
import dev.sivalabs.meetup4j.registrations.domain.vo.RegistrationCode;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class RegistrationRepositoryAdapter implements RegistrationRepository, RegistrationQueryRepository {
    private final JpaRegistrationRepository jpaRegistrationRepository;
    private final RegistrationEntityMapper registrationEntityMapper;
    private final EventsAPI eventsAPI;
    private final RegistrationViewMapper registrationViewMapper;

    RegistrationRepositoryAdapter(JpaRegistrationRepository jpaRegistrationRepository, RegistrationEntityMapper registrationEntityMapper, EventsAPI eventsAPI, RegistrationViewMapper registrationViewMapper) {
        this.jpaRegistrationRepository = jpaRegistrationRepository;
        this.registrationEntityMapper = registrationEntityMapper;
        this.eventsAPI = eventsAPI;
        this.registrationViewMapper = registrationViewMapper;
    }

    @Override
    public Optional<EventRegistration> findRegistration(EventCode eventCode, Email attendeeEmail) {
        return jpaRegistrationRepository.findByEventCodeAndAttendeeEmail(eventCode, attendeeEmail)
                .map(registrationEntityMapper::toDomain);
    }

    @Override
    public Optional<EventRegistration> findByCode(RegistrationCode code) {
        return jpaRegistrationRepository.findByCode(code)
                .map(registrationEntityMapper::toDomain);
    }

    @Override
    public void create(EventRegistration registration) {
        EventRegistrationEntity entity = registrationEntityMapper.toEntity(registration);
        jpaRegistrationRepository.save(entity);
    }

    @Override
    public void delete(EventRegistration registration) {
        jpaRegistrationRepository.deleteById(registration.getId());
    }

    // Read-only methods

    @Override
    public List<RegistrationVM> findByEventId(EventId eventId) {
        return jpaRegistrationRepository.findByEventIdOrderByRegisteredAtDesc(eventId)
                .stream()
                .map(registrationViewMapper::toRegistrationVM)
                .toList();
    }

    @Override
    public List<String> findEventAttendeeNames(EventCode eventCode) {
        return jpaRegistrationRepository.findAttendeeNamesByEventCode(eventCode);
    }

    @Override
    public Optional<RegistrationVM> findAttendeeRegistration(EventCode eventCode, Email attendeeEmail) {
        return jpaRegistrationRepository.findByEventCodeAndAttendeeEmail(eventCode, attendeeEmail)
                .map(registrationViewMapper::toRegistrationVM);
    }

    @Override
    public UserEventsVM findEvents(Email attendeeEmail) {
        List<EventId> upcomingEventIds = jpaRegistrationRepository.findUpcomingEventIdsByAttendeeEmail(attendeeEmail);
        List<EventId> pastAttendedEventIds = jpaRegistrationRepository.findPastAttendedEventIdsByAttendeeEmail(attendeeEmail);
        List<EventVM> upcomingEvents = eventsAPI.getEventsByIds(upcomingEventIds);
        List<EventVM> pastAttendedEvents = eventsAPI.getEventsByIds(pastAttendedEventIds);
        return new UserEventsVM(upcomingEvents, pastAttendedEvents);
    }
}
