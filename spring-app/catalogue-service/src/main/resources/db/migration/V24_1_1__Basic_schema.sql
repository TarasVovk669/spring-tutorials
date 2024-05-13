create schema  if not exists catalogue;

create table catalogue.t_product(
    id bigserial primary key ,
    c_name varchar(50) not null check ( length(trim(c_name))>= 3),
    c_description varchar(255),
    c_price DECIMAL
);

