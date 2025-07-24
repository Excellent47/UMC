CREATE TABLE event (
    id uuid PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL
);

CREATE TABLE event_venue (
    event_id uuid NOT NULL REFERENCES event(id) ON DELETE CASCADE,
    venue_reference_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (event_id, venue_reference_id),
    FOREIGN KEY (venue_reference_id) REFERENCES venue(reference_id) ON DELETE CASCADE
);

CREATE INDEX idx_event_start_time ON event(start_time);
CREATE INDEX idx_event_end_time ON event(end_time);
CREATE INDEX idx_event_venue ON event_venue(venue_reference_id);