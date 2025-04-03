CREATE TABLE audit_entry
(
    id         bigint       not null,
    event_date TIMESTAMP    NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    message    TEXT,
    user_id    BIGINT
);
