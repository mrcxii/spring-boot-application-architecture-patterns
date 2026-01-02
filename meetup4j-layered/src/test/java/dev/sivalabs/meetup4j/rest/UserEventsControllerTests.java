package dev.sivalabs.meetup4j.rest;

import dev.sivalabs.meetup4j.BaseIntegrationTest;
import dev.sivalabs.meetup4j.domain.models.UserEventsVM;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/test-data.sql")
class UserEventsControllerTests extends BaseIntegrationTest {
    @Test
    void shouldReturnUserEvents() {
        var result = mvc.get()
                .uri("/api/user-events?attendeeEmail=siva@gmail.com")
                .exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .convertTo(UserEventsVM.class)
                .satisfies(data -> {
                    assertThat(data).isNotNull();
                    assertThat(data.upcomingEvents()).isNotNull();
                    assertThat(data.pastAttendedEvents()).isNotNull();
                });
    }
}