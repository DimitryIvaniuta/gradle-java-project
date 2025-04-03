CREATE TABLE orders
(
    id           bigint         not null,
    order_date   TIMESTAMP      NOT NULL,
    customer_id  BIGINT         NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status       VARCHAR(50)    NOT NULL
);
