package dev.sivalabs.meetup4j.events.interfaces.rest;

import dev.sivalabs.meetup4j.BaseIntegrationTest;
import dev.sivalabs.meetup4j.events.application.query.dto.EventVM;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

@Sql("/test-data.sql")
class EventsControllerTests extends BaseIntegrationTest {
    private static final String ADMIN_CREATED_EVENT_CODE = "0NZZ6KKTZY98K";
    private static final String PAST_EVENT_CODE = "0NZZ71YDQYADK";
    private static final String DRAFT_EVENT_CODE = "0NZZ713A7YAER";

    @Test
    void shouldCreateEventSuccessfully() {
        MvcTestResult testResult = mvc.post()
                .uri("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "title": "Java User Group Meetup",
                          "description": "Monthly Java meetup for developers",
                          "startDatetime": "2025-12-20T18:00:00Z",
                          "endDatetime": "2025-12-20T21:00:00Z",
                          "type": "OFFLINE",
                          "venue": "Tech Hub, 123 Main St",
                          "ticketPrice": 0,
                          "capacity": 50
                        }
                        """)
                .exchange();

        assertThat(testResult)
                .hasStatus(CREATED)
                .bodyJson()
                .convertTo(CreateEventResponse.class)
                .satisfies(response -> {
                    assertThat(response).isNotNull();
                    assertThat(response.code()).isNotNull();
                });
    }

    @Test
    void shouldBeAbleCancelEventSuccessfully() {
        MvcTestResult cancelResult = mvc.delete()
                .uri("/api/events/{eventCode}", ADMIN_CREATED_EVENT_CODE)
                .exchange();

        assertThat(cancelResult).hasStatusOk();
    }

    @Test
    void shouldFailToCancelWhenEventAlreadyStarted() {
        // Try to cancel the past event
        MvcTestResult cancelResult = mvc.delete()
                .uri("/api/events/{eventCode}", PAST_EVENT_CODE)
                .exchange();

        assertThat(cancelResult).hasStatus(HttpStatus.UNPROCESSABLE_CONTENT);
    }

    @Test
    void shouldFindEventsSuccessfully() {
        MvcTestResult listResult = mvc.get().uri("/api/events").exchange();

        assertThat(listResult)
                .hasStatusOk()
                .bodyJson()
                .convertTo(EventsResponse.class)
                .satisfies(dto -> assertThat(dto.events()).isNotNull());
    }

    @Test
    void shouldGetEventDetailsSuccessfully() {
        MvcTestResult getResult = mvc.get()
                .uri("/api/events/{eventCode}", ADMIN_CREATED_EVENT_CODE)
                .exchange();

        assertThat(getResult).hasStatusOk().bodyJson().convertTo(EventVM.class).satisfies(details -> {
            assertThat(details.code()).isEqualTo(ADMIN_CREATED_EVENT_CODE);
            assertThat(details.title()).isEqualTo("Spring Boot 4.0 Workshop");
        });
    }

    @Test
    void shouldPublishDraftEvent() {
        MvcTestResult publishResult = mvc.patch()
                .uri("/api/events/{eventCode}/publish", DRAFT_EVENT_CODE)
                .exchange();

        assertThat(publishResult).hasStatusOk();
    }
}