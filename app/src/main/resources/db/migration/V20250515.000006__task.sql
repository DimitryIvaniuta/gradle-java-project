CREATE TABLE tasks
(
    id          BIGINT       NOT NULL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    version     BIGINT       NOT NULL
);