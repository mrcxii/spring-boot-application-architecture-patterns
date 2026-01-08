package dev.sivalabs.meetup4j.events.domain.vo;

import dev.sivalabs.meetup4j.shared.AssertUtil;

import java.time.Instant;

public record Schedule(
        Instant startDatetime,
        Instant endDatetime) {

    public Schedule {
        AssertUtil.requireNotNull(startDatetime, "Start datetime cannot be null");
        AssertUtil.requireNotNull(endDatetime, "End datetime cannot be null");
        if (endDatetime.isBefore(startDatetime)) {
            throw new IllegalArgumentException("End datetime cannot be before start datetime");
        }
    }

    public static Schedule of(Instant startDatetime, Instant endDatetime) {
        return new Schedule(startDatetime, endDatetime);
    }
}
