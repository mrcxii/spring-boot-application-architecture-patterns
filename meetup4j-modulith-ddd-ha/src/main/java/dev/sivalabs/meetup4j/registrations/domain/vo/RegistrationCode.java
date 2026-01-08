package dev.sivalabs.meetup4j.registrations.domain.vo;

import dev.sivalabs.meetup4j.shared.AssertUtil;
import dev.sivalabs.meetup4j.shared.TSIDUtil;

public record RegistrationCode(String code) {
    public RegistrationCode {
        AssertUtil.requireNotBlank(code, "Registration code");
    }

    public static RegistrationCode of(String code) {
        return new RegistrationCode(code);
    }

    public static RegistrationCode generate() {
        return new RegistrationCode(TSIDUtil.generateTsidString());
    }
}
