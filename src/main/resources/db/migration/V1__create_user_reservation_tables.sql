create table CLIQUE_USERS
(
    USERNAME                   varchar(126) not null
        constraint "CLIQUE_USERS_pk"
            primary key,
    SURNAME                    varchar(126),
    PASSWORD_USER              varchar(126),
    NAME_USER                  varchar(126),
    IS_ACCOUNT_NON_EXPIRED     bool,
    IS_ACCOUNT_NON_LOCKED      bool,
    IS_CREDENTIALS_NON_EXPIRED bool,
    IS_ENABLED                 bool,
    ROLE_USER                  varchar(126)
);

CREATE TABLE RESERVATION
(
    ID               bigint not null
        constraint "RESERVATION_PK" PRIMARY KEY,
    NAME_RESERVATION varchar(126),
    USERNAME          VARCHAR(126)
        constraint "RESERVATION_USER_ID_fk"
            references CLIQUE_USERS(USERNAME),
    NUMBER_OF_PEOPLE int,
    NUMBER_OF_TABLES int,
    TEL_NR varchar(126),
    IMPORTANCE VARCHAR(126),
    TYPE_RES VARCHAR(126),
    DATE_FOR DATE
);