package dev.sivalabs.meetup4j.events.domain.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import dev.sivalabs.meetup4j.shared.TSIDUtil;
import jakarta.validation.constraints.NotBlank;

public record EventCode(@JsonValue
                        @NotBlank(message = "Event code cannot be empty")
                        String code) {
    @JsonCreator
    public EventCode {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Event code cannot be empty");
        }
    }

    public static EventCode of(String code) {
        return new EventCode(code);
    }

    public static EventCode generate() {
        return new EventCode(TSIDUtil.generateTsidString());
    }
}
