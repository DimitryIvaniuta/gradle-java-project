CREATE TABLE events
(
    id              bigint      not null,
    event_timestamp TIMESTAMP   NOT NULL,
    event_type      VARCHAR(50) NOT NULL,
    payload         TEXT,
    PRIMARY KEY (id, event_timestamp)
) PARTITION BY RANGE (event_timestamp);

-- Partition for events in 2023
CREATE TABLE events_2023 PARTITION OF events
    FOR VALUES FROM ('2023-01-01') TO ('2024-01-01');

-- Partition for events in 2024
CREATE TABLE events_2024 PARTITION OF events
    FOR VALUES FROM ('2024-01-01') TO ('2025-01-01');

-- Partition for events in 2025
CREATE TABLE events_2025 PARTITION OF events
    FOR VALUES FROM ('2025-01-01') TO ('2026-01-01');