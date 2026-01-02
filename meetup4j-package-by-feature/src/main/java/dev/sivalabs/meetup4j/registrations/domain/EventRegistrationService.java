package dev.sivalabs.meetup4j.registrations.domain;

import dev.sivalabs.meetup4j.events.domain.EventService;
import dev.sivalabs.meetup4j.registrations.domain.events.RegistrationAdded;
import dev.sivalabs.meetup4j.registrations.domain.events.RegistrationCancelled;
import dev.sivalabs.meetup4j.shared.SpringEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class EventRegistrationService {
    private final RegistrationRepository registrationRepository;
    private final EventService eventService;
    private final RegistrationMapper registrationMapper;
    private final SpringEventPublisher eventPublisher;

    EventRegistrationService(RegistrationRepository registrationRepository,
                             EventService eventService,
                             RegistrationMapper registrationMapper,
                             SpringEventPublisher eventPublisher) {
        this.registrationRepository = registrationRepository;
        this.eventService = eventService;
        this.registrationMapper = registrationMapper;
        this.eventPublisher = eventPublisher;
    }

    public UserEventsVM getAttendeeEvents(String attendeeEmail) {
        var upcomingEventIds = registrationRepository.findUpcomingEventIdsByAttendeeEmail(attendeeEmail);
        var pastAttendedEventIds = registrationRepository.findPastAttendedEventIdsByAttendeeEmail(attendeeEmail);

        var upcomingEvents = eventService.getEventsByIds(upcomingEventIds);
        var pastAttendedEvents = eventService.getEventsByIds(pastAttendedEventIds);

        return new UserEventsVM(upcomingEvents, pastAttendedEvents);
    }

    @Transactional
    public String registerForEvent(RegisterAttendeeCmd cmd) {
        var existingRegistration = registrationRepository.findByEventCodeAndAttendeeEmail(cmd.eventCode(), cmd.attendeeEmail());
        if(existingRegistration.isPresent()) {
            return existingRegistration.get().getCode();
        }
        var eventVM = eventService.getByCode(cmd.eventCode());
        eventService.reserveSlotForEvent(eventVM.id());

        var registration = new EventRegistrationEntity();
        registration.setCode(UUID.randomUUID().toString());
        registration.setEventId(eventVM.id());
        registration.setEventCode(cmd.eventCode());
        registration.setAttendeeName(cmd.attendeeName());
        registration.setAttendeeEmail(cmd.attendeeEmail());
        registration.setRegisteredAt(Instant.now());

        registrationRepository.save(registration);
        eventPublisher.publish(new RegistrationAdded(registration.getEventId(), registration.getAttendeeEmail()));
        return registration.getCode();
    }

    @Transactional
    public void cancelRegistration(CancelRegistrationCmd cmd) {
        var registration = registrationRepository.getByCode(cmd.registrationCode());
        var event = eventService.getByCode(registration.getEventCode());
        if (event.startDatetime().isBefore(Instant.now())) {
            throw new RegistrationCancellationException("Cannot cancel registration for past events");
        }
        eventService.freeSlotForEvent(event.id());
        registrationRepository.delete(registration);
        eventPublisher.publish(new RegistrationCancelled(registration.getEventId(), registration.getAttendeeEmail()));
    }

    public List<RegistrationVM> getEventRegistrations(String eventCode) {
        var eventVM = eventService.getByCode(eventCode);
        return registrationRepository.findByEventIdOrderByRegisteredAtDesc(eventVM.id())
                .stream().map(registrationMapper::toRegistrationVM).toList();
    }

    public AttendeesVM getEventAttendees(String eventCode) {
        return new AttendeesVM(registrationRepository.findAttendeeNamesByEventCode(eventCode));
    }

    public UserRegistrationStatus getRegistrationStatus(String eventCode, String attendeeEmail) {
        var registrationOpt = registrationRepository.findByEventCodeAndAttendeeEmail(eventCode, attendeeEmail);
        if (registrationOpt.isPresent()) {
            var registration = registrationOpt.get();
            return new UserRegistrationStatus(true, registration.getCode(), registration.getRegisteredAt());
        } else {
            return UserRegistrationStatus.notRegistered();
        }
    }
}
