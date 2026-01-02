package dev.sivalabs.meetup4j.registrations.domain;

import dev.sivalabs.meetup4j.events.EventsAPI;
import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.registrations.domain.vo.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EventRegistrationQueryService {
    private final RegistrationRepository registrationRepository;
    private final EventsAPI eventsAPI;
    private final RegistrationMapper registrationMapper;

    EventRegistrationQueryService(RegistrationRepository registrationRepository,
                                  EventsAPI eventsAPI,
                                  RegistrationMapper registrationMapper) {
        this.registrationRepository = registrationRepository;
        this.eventsAPI = eventsAPI;
        this.registrationMapper = registrationMapper;
    }

    public UserEventsVM getAttendeeEvents(Email attendeeEmail) {
        var upcomingEventIds = registrationRepository.findUpcomingEventIdsByAttendeeEmail(attendeeEmail);
        var pastAttendedEventIds = registrationRepository.findPastAttendedEventIdsByAttendeeEmail(attendeeEmail);

        var upcomingEvents = eventsAPI.getEventsByIds(upcomingEventIds);
        var pastAttendedEvents = eventsAPI.getEventsByIds(pastAttendedEventIds);

        return new UserEventsVM(upcomingEvents, pastAttendedEvents);
    }

    public List<RegistrationVM> getEventRegistrations(EventCode eventCode) {
        var eventVM = eventsAPI.getEventByCode(eventCode);
        return registrationRepository.findByEventIdOrderByRegisteredAtDesc(EventId.of(eventVM.id()))
                .stream().map(registrationMapper::toRegistrationVM).toList();
    }

    public AttendeesVM getEventAttendees(EventCode eventCode) {
        return new AttendeesVM(registrationRepository.findAttendeeNamesByEventCode(eventCode));
    }

    public UserRegistrationStatus getRegistrationStatus(EventCode eventCode, Email attendeeEmail) {
        Optional<EventRegistrationEntity> registrationOpt = registrationRepository.findByEventCodeAndAttendeeEmail(eventCode, attendeeEmail);
        if (registrationOpt.isPresent()) {
            EventRegistrationEntity registration = registrationOpt.get();
            return new UserRegistrationStatus(true, registration.getCode().code(), registration.getRegisteredAt());
        } else {
            return UserRegistrationStatus.notRegistered();
        }
    }
}
