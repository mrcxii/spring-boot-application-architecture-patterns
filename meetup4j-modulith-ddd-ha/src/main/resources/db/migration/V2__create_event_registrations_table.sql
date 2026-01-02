CREATE TABLE event_registrations
(
    id             BIGINT       NOT NULL PRIMARY KEY,
    code           VARCHAR(50)  NOT NULL UNIQUE,
    event_id       BIGINT       NOT NULL,
    event_code     VARCHAR(50)  NOT NULL,
    attendee_name  VARCHAR(200) NOT NULL,
    attendee_email VARCHAR(200) NOT NULL,
    registered_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP,
    version        INTEGER               DEFAULT 0,
    CONSTRAINT fk_event_registrations_event FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
    CONSTRAINT uq_event_registrations_event_attendee UNIQUE (event_id, attendee_email)
);
