package dev.sivalabs.meetup4j.registrations.application.command;

import dev.sivalabs.meetup4j.events.application.EventsAPI;
import dev.sivalabs.meetup4j.events.domain.vo.EventId;
import dev.sivalabs.meetup4j.registrations.application.command.dto.RegisterAttendeeCmd;
import dev.sivalabs.meetup4j.registrations.domain.model.EventRegistration;
import dev.sivalabs.meetup4j.registrations.domain.repository.RegistrationRepository;
import dev.sivalabs.meetup4j.registrations.domain.vo.RegistrationCode;
import dev.sivalabs.meetup4j.shared.DomainEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterAttendeeUseCase {
    private final RegistrationRepository registrationRepository;
    private final EventsAPI eventsAPI;
    private final DomainEventPublisher eventPublisher;

    public RegisterAttendeeUseCase(RegistrationRepository registrationRepository,
                                   EventsAPI eventsAPI,
                                   DomainEventPublisher eventPublisher) {
        this.registrationRepository = registrationRepository;
        this.eventsAPI = eventsAPI;
        this.eventPublisher = eventPublisher;
    }

    public RegistrationCode registerForEvent(RegisterAttendeeCmd cmd) {
        var existingRegistration = registrationRepository.findRegistration(cmd.eventCode(), cmd.attendeeEmail());
        if(existingRegistration.isPresent()) {
            return existingRegistration.get().getCode();
        }
        var eventVM = eventsAPI.getEventByCode(cmd.eventCode());
        eventsAPI.reserveSlotForEvent(EventId.of(eventVM.id()));

        var registration = EventRegistration.create(
                EventId.of(eventVM.id()),
                cmd.eventCode(),
                cmd.attendeeName(),
                cmd.attendeeEmail()
        );

        registrationRepository.create(registration);
        eventPublisher.publish(registration.pullDomainEvents());
        return registration.getCode();
    }
}
