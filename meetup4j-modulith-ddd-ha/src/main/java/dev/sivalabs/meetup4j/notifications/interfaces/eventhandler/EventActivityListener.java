package dev.sivalabs.meetup4j.notifications.interfaces.eventhandler;

import dev.sivalabs.meetup4j.events.domain.event.EventCancelled;
import dev.sivalabs.meetup4j.events.domain.event.EventCreated;
import dev.sivalabs.meetup4j.events.domain.event.EventPublished;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
class EventActivityListener {
    private static final Logger log = LoggerFactory.getLogger(EventActivityListener.class);

    @ApplicationModuleListener
    public void handle(EventCreated event) {
        log.info("A new Event is created with code:{} and title: {}", event.code(), event.title());
    }

    @ApplicationModuleListener
    public void handle(EventPublished event) {
        log.info("Event with code:{} and title: {} is published", event.code(), event.title());
    }

    @ApplicationModuleListener
    public void handle(EventCancelled event) {
        log.info("Event with code:{} and title: {} is cancelled", event.code(), event.title());
    }
}
