CREATE TABLE IF NOT EXISTS characteristics (
	id          BIGINT NOT NULL auto_increment PRIMARY KEY,
	series_id   BIGINT REFERENCES series,
	name          varchar(255) NOT NULL,
	UNIQUE (series_id, name)
);