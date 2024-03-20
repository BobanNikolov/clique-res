create table clique_res.user_account
(
    id    serial
        constraint user_account_pk primary key
        constraint user_account_pk_unique unique,
    username  varchar(255) ,
    email  varchar(255) not null ,
    password  varchar(255) not null ,
    first_name  varchar(255) not null ,
    last_name  varchar(255) not null ,
    display_name  varchar(255) not null
);