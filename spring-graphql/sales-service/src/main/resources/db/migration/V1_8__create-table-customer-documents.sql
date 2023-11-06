CREATE TABLE IF NOT EXISTS customer_documents (
  id                      BIGINT auto_increment NOT NULL PRIMARY KEY,
  customer_id             BIGINT,
  document_type             varchar(255) NOT NULL,
  document_path             varchar(255) NOT NULL,
  FOREIGN KEY (customer_id) REFERENCES customers (id)
);
