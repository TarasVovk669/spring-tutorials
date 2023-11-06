CREATE TABLE IF NOT EXISTS features (
	id                  BIGINT NOT NULL auto_increment PRIMARY KEY,
	model_id            BIGINT REFERENCES models,
	name                  varchar(255) NOT NULL,
	active_by_default     boolean NOT NULL,
	active_by_request     boolean NOT NULL,
	installation_price    decimal(38, 2) NOT NULL DEFAULT 0,
	is_safety             boolean NOT NULL,
	is_entertainment      boolean NOT NULL,
	is_performance        boolean NOT NULL,
	is_convenience        boolean NOT NULL,
	is_display            boolean NOT NULL
);
