CREATE TABLE IF NOT EXISTS series
(
    id              BIGINT       NOT NULL auto_increment PRIMARY KEY,
    manufacturer_id BIGINT REFERENCES manufacturers,
    name            varchar(255) NOT NULL,
    UNIQUE (manufacturer_id, name)
);