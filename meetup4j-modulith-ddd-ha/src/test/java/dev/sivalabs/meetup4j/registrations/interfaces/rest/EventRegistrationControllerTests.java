package dev.sivalabs.meetup4j.registrations.interfaces.rest;

import dev.sivalabs.meetup4j.BaseIntegrationTest;
import dev.sivalabs.meetup4j.registrations.application.query.dto.AttendeesVM;
import dev.sivalabs.meetup4j.registrations.application.query.dto.EventRegistrations;
import dev.sivalabs.meetup4j.registrations.application.query.dto.UserRegistrationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static org.assertj.core.api.Assertions.assertThat;

@Sql("/test-data.sql")
class EventRegistrationControllerTests extends BaseIntegrationTest {
    private static final String ADMIN_CREATED_EVENT_CODE = "0NZZ6KKTZY98K";
    private static final String USER_EVENT_REGISTRATION_CODE = "0NZZ75Q4KY88Y";

    @Test
    void shouldRegisterForEventSuccessfully() {
        MvcTestResult registerResult = mvc.post()
                .uri("/api/registrations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "eventCode": "%s",
                          "attendeeName": "Shiva",
                          "attendeeEmail": "shiva@gmail.com"
                        }
                        """.formatted(ADMIN_CREATED_EVENT_CODE))
                .exchange();

        assertThat(registerResult).hasStatus(HttpStatus.CREATED);
    }

    @Test
    void shouldGetEventRegistrations() {
        MvcTestResult listResult = mvc.get()
                .uri("/api/events/{eventCode}/registrations", ADMIN_CREATED_EVENT_CODE)
                .exchange();

        assertThat(listResult)
                .hasStatusOk()
                .bodyJson()
                .convertTo(EventRegistrations.class)
                .satisfies(resp -> {
                    assertThat(resp).isNotNull();
                    assertThat(resp.registrations()).isNotNull();
                    assertThat(resp.registrations().size()).isGreaterThanOrEqualTo(1);
                });
    }

    @Test
    void shouldCancelRegistrationSuccessfully() {
        MvcTestResult cancelResult = mvc.delete()
                .uri("/api/registrations/" + USER_EVENT_REGISTRATION_CODE)
                .exchange();

        assertThat(cancelResult).hasStatusOk();
    }

    @Test
    void shouldGetEventAttendees() {
        MvcTestResult attendeesResult = mvc.get()
                .uri("/api/events/{eventCode}/registrations/attendees", ADMIN_CREATED_EVENT_CODE)
                .exchange();

        assertThat(attendeesResult)
                .hasStatusOk()
                .bodyJson()
                .convertTo(AttendeesVM.class)
                .satisfies(resp -> {
                    assertThat(resp).isNotNull();
                    assertThat(resp.names()).isNotNull();
                });
    }

    @Test
    void shouldGetRegistrationStatusForRegisteredAttendee() {
        MvcTestResult statusResult = mvc.get()
                .uri("/api/events/{eventCode}/registrations/status?attendeeEmail={attendeeEmail}", ADMIN_CREATED_EVENT_CODE, "siva@gmail.com")
                .exchange();

        assertThat(statusResult)
                .hasStatusOk()
                .bodyJson()
                .convertTo(UserRegistrationStatus.class)
                .satisfies(status -> {
                    assertThat(status).isNotNull();
                    assertThat(status.registered()).isTrue();
                    assertThat(status.registrationCode()).isNotNull();
                    assertThat(status.registeredAt()).isNotNull();
                });
    }

    @Test
    void shouldReturnNotRegisteredStatusForUnregisteredAttendee() {
        MvcTestResult statusResult = mvc.get()
                .uri("/api/events/{eventCode}/registrations/status?attendeeEmail={attendeeEmail}", ADMIN_CREATED_EVENT_CODE, "neha@gmail.com")
                .exchange();

        assertThat(statusResult)
                .hasStatusOk()
                .bodyJson()
                .convertTo(UserRegistrationStatus.class)
                .satisfies(status -> {
                    assertThat(status).isNotNull();
                    assertThat(status.registered()).isFalse();
                    assertThat(status.registrationCode()).isNull();
                    assertThat(status.registeredAt()).isNull();
                });
    }
}