package dev.sivalabs.meetup4j.events.domain.vo;

import dev.sivalabs.meetup4j.shared.TSIDUtil;

public record EventId(Long id) {
    public EventId {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("Event id cannot be null");
        }
    }

    public static EventId of(Long id) {
        return new EventId(id);
    }

    public static EventId generate() {
        return new EventId(TSIDUtil.generateTsidLong());
    }
}
