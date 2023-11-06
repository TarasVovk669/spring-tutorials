CREATE TABLE IF NOT EXISTS manufacturers (
	id              BIGINT NOT NULL PRIMARY KEY auto_increment,
	name              varchar(255) NOT NULL UNIQUE,
	origin_country    varchar(255) NOT NULL,
	description       varchar(255)
);