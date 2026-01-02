package dev.sivalabs.meetup4j.registrations.domain.vo;

import dev.sivalabs.meetup4j.shared.TSIDUtil;

public record RegistrationId(Long id) {
    public RegistrationId {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("Registration id cannot be null");
        }
    }

    public static RegistrationId of(Long id) {
        return new RegistrationId(id);
    }

    public static RegistrationId generate() {
        return new RegistrationId(TSIDUtil.generateTsidLong());
    }
}
