alter table clique_res.reservation
    add reservation_type varchar(200) default 'ORDINARY' not null;

alter table clique_res.reservation
    add price_of_reservation bigint;

