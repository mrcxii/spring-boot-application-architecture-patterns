package dev.sivalabs.meetup4j.events.interfaces.rest.converters;

import dev.sivalabs.meetup4j.events.domain.vo.EventCode;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEventCodeConverter implements Converter<String, EventCode> {

    @Override
    public EventCode convert(String source) {
        return new EventCode(source);
    }
}