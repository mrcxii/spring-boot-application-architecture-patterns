package dev.sivalabs.meetup4j;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "app")
public record ApplicationProperties(
    @DefaultValue("support@sivalabs.dev")
    String supportEmail
){}
