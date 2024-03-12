create table clique_res.event
(
    id    serial
        constraint event_pk primary key
        constraint event_pk_unique unique,
    name_of_event  varchar(255) not null ,
    date_of_event date not null
);