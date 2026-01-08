package dev.sivalabs.meetup4j.events.domain.vo;

import dev.sivalabs.meetup4j.shared.AssertUtil;
import dev.sivalabs.meetup4j.shared.TSIDUtil;

public record EventCode(String code) {

    public EventCode {
        AssertUtil.requireNotBlank(code, "Event code");
    }

    public static EventCode of(String code) {
        return new EventCode(code);
    }

    public static EventCode generate() {
        return new EventCode(TSIDUtil.generateTsidString());
    }
}
