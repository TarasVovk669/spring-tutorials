create table t_user
(
    id      bigint auto_increment,
    name    varchar(50),
    balance int,
    primary key (id)
);

create table t_transactions
(
    id bigint auto_increment,
    user_id bigint,
    amount int,
    date timestamp,
    foreign key (user_id) references t_user(id) on delete cascade
);

insert into t_user
(name, balance)
values
    ('sam', 1000),
    ('mike', 1200),
    ('jake', 800),
    ('marshal', 2000);