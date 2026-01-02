package dev.sivalabs.meetup4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Meetup4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Meetup4jApplication.class, args);
    }
}
