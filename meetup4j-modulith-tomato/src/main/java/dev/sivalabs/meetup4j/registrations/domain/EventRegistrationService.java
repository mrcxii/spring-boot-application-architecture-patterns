package dev.sivalabs.meetup4j.registrations.domain;

import dev.sivalabs.meetup4j.events.EventsAPI;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.registrations.domain.events.RegistrationAdded;
import dev.sivalabs.meetup4j.registrations.domain.events.RegistrationCancelled;
import dev.sivalabs.meetup4j.registrations.domain.vo.RegistrationCode;
import dev.sivalabs.meetup4j.shared.SpringEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class EventRegistrationService {
    private final RegistrationRepository registrationRepository;
    private final EventsAPI eventsAPI;
    private final SpringEventPublisher eventPublisher;

    EventRegistrationService(RegistrationRepository registrationRepository,
                             EventsAPI eventsAPI,
                             SpringEventPublisher eventPublisher) {
        this.registrationRepository = registrationRepository;
        this.eventsAPI = eventsAPI;
        this.eventPublisher = eventPublisher;
    }

    public RegistrationCode registerForEvent(RegisterAttendeeCmd cmd) {
        Optional<EventRegistrationEntity> existing =
                registrationRepository.findByEventCodeAndAttendeeEmail(cmd.eventCode(), cmd.attendeeEmail());
        if(existing.isPresent()) {
            return existing.get().getCode();
        }
        var eventVM = eventsAPI.getEventByCode(cmd.eventCode());
        eventsAPI.reserveSlotForEvent(EventId.of(eventVM.id()));

        EventRegistrationEntity registration = EventRegistrationEntity.create(
                EventId.of(eventVM.id()),
                cmd.eventCode(),
                cmd.attendeeName(),
                cmd.attendeeEmail()
        );

        registrationRepository.save(registration);
        eventPublisher.publish(new RegistrationAdded(registration.getEventId().id(), registration.getAttendeeEmail().value()));
        return registration.getCode();
    }

    public void cancelRegistration(CancelRegistrationCmd cmd) {
        EventRegistrationEntity registration = registrationRepository.getByCode(cmd.registrationCode());
        var event = eventsAPI.getEventByCode(registration.getEventCode());
        if (event.startDatetime().isBefore(Instant.now())) {
            throw new RegistrationCancellationException("Cannot cancel registration for past events");
        }
        eventsAPI.freeSlotForEvent(registration.getEventId());
        registrationRepository.delete(registration);
        eventPublisher.publish(new RegistrationCancelled(registration.getEventId().id(), registration.getAttendeeEmail().value()));
    }
}
