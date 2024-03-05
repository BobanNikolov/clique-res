create table clique_res.reservation
(
    id    serial
        constraint reservation_pk primary key
        constraint reservation_pk_unique unique,
    name_reservation  varchar(255) not null ,
    num_of_people bigint,
    num_of_tables bigint not null ,
    created_by bigint,
    event bigint
);