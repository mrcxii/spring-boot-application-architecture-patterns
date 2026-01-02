package dev.sivalabs.meetup4j.events.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import dev.sivalabs.meetup4j.shared.TSIDUtil;

public record EventCode(@JsonValue String code) {
    @JsonCreator
    public EventCode {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Event code cannot be null");
        }
    }

    public static EventCode of(String code) {
        return new EventCode(code);
    }

    public static EventCode generate() {
        return new EventCode(TSIDUtil.generateTsidString());
    }
}
