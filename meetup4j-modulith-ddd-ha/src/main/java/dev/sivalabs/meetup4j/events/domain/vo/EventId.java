package dev.sivalabs.meetup4j.events.domain.vo;

import dev.sivalabs.meetup4j.shared.AssertUtil;
import dev.sivalabs.meetup4j.shared.TSIDUtil;

public record EventId(Long id) {
    public EventId {
        AssertUtil.requireNotNull(id, "Event id");
        AssertUtil.requireMin(id, 0, "Event id cannot be negative");
    }

    public static EventId of(Long id) {
        return new EventId(id);
    }

    public static EventId generate() {
        return new EventId(TSIDUtil.generateTsidLong());
    }
}
