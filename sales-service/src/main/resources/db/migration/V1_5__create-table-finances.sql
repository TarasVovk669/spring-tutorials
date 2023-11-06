CREATE TABLE IF NOT EXISTS finances
(
    id              BIGINT  auto_increment NOT NULL PRIMARY KEY,
    sales_order_id  BIGINT,
    base_amount     decimal(38, 2) NOT NULL,
    tax_amount      decimal(38, 2) NOT NULL,
    discount_amount decimal(38, 2) NOT NULL,
    is_loan         boolean        NOT NULL,
    FOREIGN KEY (sales_order_id) REFERENCES sales_orders (id)

);