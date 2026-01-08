package dev.sivalabs.meetup4j.registrations.domain.vo;

import dev.sivalabs.meetup4j.shared.AssertUtil;
import dev.sivalabs.meetup4j.shared.TSIDUtil;

public record RegistrationId(Long id) {
    public RegistrationId {
        AssertUtil.requireNotNull(id, "Registration id");
        AssertUtil.requireMin(id, 0, "Registration id cannot be negative");
    }

    public static RegistrationId of(Long id) {
        return new RegistrationId(id);
    }

    public static RegistrationId generate() {
        return new RegistrationId(TSIDUtil.generateTsidLong());
    }
}
