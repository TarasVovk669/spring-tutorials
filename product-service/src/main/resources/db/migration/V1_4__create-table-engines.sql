CREATE TABLE IF NOT EXISTS engines (
	id 			BIGINT NOT NULL auto_increment PRIMARY KEY,
	description	varchar(255),
	horse_power	int NOT NULL DEFAULT 0,
	torque		int NOT NULL DEFAULT 0,
	capacity_cc	int NOT NULL DEFAULT 0
);