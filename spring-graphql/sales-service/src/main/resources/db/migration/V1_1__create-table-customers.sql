CREATE TABLE IF NOT EXISTS customers
(
    id         BIGINT auto_increment NOT NULL PRIMARY KEY,
    full_name  varchar(255)          NOT NULL,
    birth_date date                  NOT NULL,
    phone      varchar(255)          NOT NULL,
    email      varchar(255)          NOT NULL UNIQUE
);