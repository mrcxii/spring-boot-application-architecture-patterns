package dev.sivalabs.meetup4j.registrations.interfaces.rest.converters;

import dev.sivalabs.meetup4j.registrations.domain.vo.RegistrationCode;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRegistrationCodeConverter implements Converter<String, RegistrationCode> {

    @Override
    public RegistrationCode convert(String source) {
        return new RegistrationCode(source);
    }
}