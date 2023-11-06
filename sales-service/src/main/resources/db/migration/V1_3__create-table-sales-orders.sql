CREATE TABLE IF NOT EXISTS sales_orders (
	id            	BIGINT auto_increment NOT NULL PRIMARY KEY,
	customer_id   	BIGINT,
    created_date_time	timestamp NOT NULL,
	order_number		varchar(255) NOT NULL UNIQUE,
    FOREIGN KEY (customer_id) REFERENCES customers (id)
);