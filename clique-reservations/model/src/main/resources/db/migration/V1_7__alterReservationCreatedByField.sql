alter table clique_res.reservation
    rename column created_by to created_by_id;

alter table clique_res.reservation
    add constraint user_account_fk
        foreign key (created_by_id) references clique_res.user_account;
