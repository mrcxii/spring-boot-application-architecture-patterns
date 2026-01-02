CREATE SEQUENCE event_id_seq START WITH 101 INCREMENT BY 50;

CREATE TABLE events
(
    id                  BIGINT         NOT NULL DEFAULT nextval('event_id_seq') PRIMARY KEY,
    code                VARCHAR(50)    NOT NULL UNIQUE,
    title               VARCHAR(255)   NOT NULL,
    description         TEXT           NOT NULL,
    start_datetime      TIMESTAMPTZ    NOT NULL,
    end_datetime        TIMESTAMPTZ    NOT NULL,
    event_type          VARCHAR(50)    NOT NULL,
    venue               VARCHAR(500),
    virtual_link        VARCHAR(500),
    image_url           VARCHAR(500),
    ticket_price        DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    capacity            INTEGER,
    registrations_count INTEGER                 DEFAULT 0,
    status              VARCHAR(50)    NOT NULL DEFAULT 'DRAFT',
    created_at          TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP,
    version             INTEGER                 DEFAULT 0,
    CONSTRAINT chk_events_datetime CHECK (end_datetime >= start_datetime),
    CONSTRAINT chk_events_capacity CHECK (capacity IS NULL OR capacity > 0),
    CONSTRAINT chk_events_price CHECK (ticket_price >= 0)
);

CREATE INDEX idx_events_code ON events (code);
CREATE INDEX idx_events_start_datetime ON events (start_datetime);
CREATE INDEX idx_events_type ON events (event_type);
CREATE INDEX idx_events_status ON events (status);
