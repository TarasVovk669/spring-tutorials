CREATE TABLE IF NOT EXISTS sales_order_items (
	id                  BIGINT auto_increment NOT NULL PRIMARY KEY,
	sales_order_id      BIGINT ,
	quantity 	          integer NOT NULL,
	model_id		      BIGINT NOT NULL,
    FOREIGN KEY (sales_order_id) REFERENCES sales_orders(id)
);


create table sales_order_items_seq (next_val bigint);
insert into sales_order_items_seq values ( 1 )