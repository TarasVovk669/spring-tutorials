create schema if not exists user_management;

create table user_management.t_user
(
    id         bigserial primary key,
    c_username varchar(50) not null check ( length(trim(c_username)) > 0),
    c_password varchar
);

create table user_management.t_authority
(
    id         bigserial primary key,
    c_authority varchar not null
);

create table user_management.t_user_authority
(
    id         bigserial primary key,
    user_id bigint not null references user_management.t_user(id),
    authority_id bigint not null references user_management.t_user_authority(id),
    constraint uk_user_authority unique (user_id,authority_id)
);

