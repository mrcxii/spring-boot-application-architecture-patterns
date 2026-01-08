package dev.sivalabs.meetup4j.events.domain.vo;

import dev.sivalabs.meetup4j.shared.AssertUtil;

public record Capacity(Integer value) {
    public static final Capacity UNLIMITED = new Capacity(null);

    public Capacity {
        if(value != null) {
            AssertUtil.requireSize(value, 0, 10000, "Capacity");
        }
    }

    public static Capacity of(Integer capacity) {
        if (capacity == null) {
            return UNLIMITED;
        }
        return new Capacity(capacity);
    }
}
