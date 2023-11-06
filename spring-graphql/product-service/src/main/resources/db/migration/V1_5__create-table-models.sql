CREATE TABLE IF NOT EXISTS models (
	id 					BIGINT NOT NULL auto_increment PRIMARY KEY,
	series_id 			BIGINT REFERENCES series,
	name                  varchar(255) NOT NULL,
	on_the_road_price 	decimal(38, 2) NOT NULL DEFAULT 0,
	length_mm				int NOT NULL DEFAULT 0,
	width_mm				int NOT NULL DEFAULT 0,
	height_mm				int NOT NULL DEFAULT 0,
	exterior_color		varchar(255) NOT NULL,
	interior_color		varchar(255) NOT NULL,
	release_year			int NOT NULL DEFAULT 0,
	transmission			varchar(255) NOT null,
	body_type				varchar(255) NOT NULL,
	fuel					varchar(255) NOT NULL,
	doors					int NOT NULL DEFAULT 0,
	airbags				int NOT NULL DEFAULT 0,
	is_available			boolean NOT NULL,
	engine_id           BIGINT REFERENCES engines
);