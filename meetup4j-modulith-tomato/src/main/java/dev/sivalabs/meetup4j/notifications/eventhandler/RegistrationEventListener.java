package dev.sivalabs.meetup4j.notifications.eventhandler;

import dev.sivalabs.meetup4j.registrations.domain.events.RegistrationAdded;
import dev.sivalabs.meetup4j.registrations.domain.events.RegistrationCancelled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
public class RegistrationEventListener {

    private static final Logger log = LoggerFactory.getLogger(RegistrationEventListener.class);

    @ApplicationModuleListener
    public void handle(RegistrationAdded event) {
        log.info("Registration created for event {} by {}", event.eventId(), event.attendeeEmail());
    }

    @ApplicationModuleListener
    public void handle(RegistrationCancelled event) {
        log.info("Registration cancelled for event {} by {}", event.eventId(), event.attendeeEmail());
    }
}
