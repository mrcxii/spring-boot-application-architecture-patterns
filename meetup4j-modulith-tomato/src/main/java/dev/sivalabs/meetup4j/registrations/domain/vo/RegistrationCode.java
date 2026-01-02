package dev.sivalabs.meetup4j.registrations.domain.vo;

import dev.sivalabs.meetup4j.shared.TSIDUtil;

public record RegistrationCode(String code) {
    public RegistrationCode {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration code cannot be null");
        }
    }

    public static RegistrationCode of(String code) {
        return new RegistrationCode(code);
    }

    public static RegistrationCode generate() {
        return new RegistrationCode(TSIDUtil.generateTsidString());
    }
}
