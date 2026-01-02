package dev.sivalabs.meetup4j.registrations.application.query;

import dev.sivalabs.meetup4j.events.application.EventsAPI;
import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.registrations.application.query.dto.AttendeesVM;
import dev.sivalabs.meetup4j.registrations.application.query.dto.RegistrationVM;
import dev.sivalabs.meetup4j.registrations.application.query.dto.UserEventsVM;
import dev.sivalabs.meetup4j.registrations.application.query.dto.UserRegistrationStatus;
import dev.sivalabs.meetup4j.registrations.domain.vo.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RegistrationQueryService {
    private final RegistrationQueryRepository registrationQueryRepository;
    private final EventsAPI eventsAPI;

    public RegistrationQueryService(RegistrationQueryRepository registrationQueryRepository,
                                    EventsAPI eventsAPI) {
        this.registrationQueryRepository = registrationQueryRepository;
        this.eventsAPI = eventsAPI;
    }

    public UserEventsVM getAttendeeEvents(Email attendeeEmail) {
        return registrationQueryRepository.findEvents(attendeeEmail);
    }

    public List<RegistrationVM> getEventRegistrations(EventCode eventCode) {
        var event = eventsAPI.getEventByCode(eventCode);
        return registrationQueryRepository.findByEventId(EventId.of(event.id()));
    }

    public AttendeesVM getEventAttendees(EventCode eventCode) {
        return new AttendeesVM(registrationQueryRepository.findEventAttendeeNames(eventCode));
    }

    public UserRegistrationStatus getRegistrationStatus(EventCode eventCode, Email attendeeEmail) {
        var registrationOpt = registrationQueryRepository.findAttendeeRegistration(eventCode, attendeeEmail);
        if (registrationOpt.isPresent()) {
            var registration = registrationOpt.get();
            return new UserRegistrationStatus(true, registration.code(), registration.registeredAt());
        } else {
            return UserRegistrationStatus.notRegistered();
        }
    }
}
