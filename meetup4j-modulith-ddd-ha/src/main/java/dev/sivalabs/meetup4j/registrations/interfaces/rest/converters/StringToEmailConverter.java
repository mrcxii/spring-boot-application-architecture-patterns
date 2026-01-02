package dev.sivalabs.meetup4j.registrations.interfaces.rest.converters;

import dev.sivalabs.meetup4j.registrations.domain.vo.Email;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEmailConverter implements Converter<String, Email> {

    @Override
    public Email convert(String source) {
        return new Email(source);
    }
}