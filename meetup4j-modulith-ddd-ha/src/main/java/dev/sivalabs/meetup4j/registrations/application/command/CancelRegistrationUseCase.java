package dev.sivalabs.meetup4j.registrations.application.command;

import dev.sivalabs.meetup4j.events.application.EventsAPI;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.registrations.application.command.dto.CancelRegistrationCmd;
import dev.sivalabs.meetup4j.registrations.domain.exception.RegistrationCancellationException;
import dev.sivalabs.meetup4j.registrations.domain.repository.RegistrationRepository;
import dev.sivalabs.meetup4j.shared.DomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class CancelRegistrationUseCase {
    private final RegistrationRepository registrationRepository;
    private final EventsAPI eventsAPI;
    private final DomainEventPublisher eventPublisher;

    public CancelRegistrationUseCase(RegistrationRepository registrationRepository,
                                     EventsAPI eventsAPI,
                                     DomainEventPublisher eventPublisher) {
        this.registrationRepository = registrationRepository;
        this.eventsAPI = eventsAPI;
        this.eventPublisher = eventPublisher;
    }

    public void cancelRegistration(CancelRegistrationCmd cmd) {
        var registration = registrationRepository.getByCode(cmd.registrationCode());
        var event = eventsAPI.getEventByCode(registration.getEventCode());
        if (event.startDatetime().isBefore(Instant.now())) {
            throw new RegistrationCancellationException("Cannot cancel registration for past events");
        }
        eventsAPI.freeSlotForEvent(EventId.of(event.id()));
        registration.cancel();
        registrationRepository.delete(registration);
        eventPublisher.publish(registration.pullDomainEvents());
    }
}
