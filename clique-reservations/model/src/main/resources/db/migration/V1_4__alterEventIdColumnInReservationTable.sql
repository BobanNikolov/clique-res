alter table clique_res.reservation
    rename column event to event_id;

alter table clique_res.reservation
    add constraint event_fk
        foreign key (event_id) references clique_res.event;
