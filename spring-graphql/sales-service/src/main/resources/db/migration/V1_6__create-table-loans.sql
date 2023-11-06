CREATE TABLE IF NOT EXISTS loans (
	id                      BIGINT auto_increment NOT NULL PRIMARY KEY,
	finance_id              BIGINT,
	finance_company 	        varchar(255) NOT NULL,
	contact_person_name       varchar(255) NOT NULL,
	contact_person_phone      varchar(255) NOT NULL,
	contact_person_email      varchar(255) NOT NULL,
    FOREIGN KEY (finance_id) REFERENCES finances (id)
);