CREATE TABLE EVENT
(
    ID               bigint not null
        constraint "EVENT_PK" PRIMARY KEY,
    EVENT_NAME varchar(126),
    EVENT_AT DATE
);

ALTER TABLE RESERVATION
    ADD COLUMN EVENT_ID BIGINT;

alter table reservation
    add constraint "reservation_EVENT_ID_fk"
        foreign key (EVENT_ID) references EVENT (ID);