CREATE TABLE IF NOT EXISTS addresses
(
    id          BIGINT auto_increment NOT NULL PRIMARY KEY,
    customer_id BIGINT,
    street      varchar(255)          NOT NULL,
    city        varchar(255)          NOT NULL,
    zipcode     varchar(255)          NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (id)
);