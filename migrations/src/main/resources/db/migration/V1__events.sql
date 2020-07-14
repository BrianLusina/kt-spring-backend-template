CREATE TABLE IF NOT EXISTS events
(
    id                 int          NOT NULL,
    created_on         timestamp default current_timestamp,
    updated_on         timestamp default current_timestamp,
    identifier         varchar(255) NOT NULL,
    organization       varchar(50)  NOT NULL,
    title              varchar(255) NOT NULL,
    description        varchar(255) NOT NULL,
    location           varchar(255) NOT NULL,
    banner_image_url   varchar(255) NOT NULL,
    image_url          varchar(255) NOT NULL,
    event_type         varchar(20)  NOT NULL,
    full_names         varchar(255) NOT NULL,
    generated_password varchar(8)   NOT NULL,
    date_of_event      timestamp    NOT NULL,
    start_time         time         NOT NULL,
    end_time           time         NOT NULL,
    constraint events_pk
        primary key (id)
);

create unique index events_identifier_uindex
    on events (identifier);

CREATE TABLE IF NOT EXISTS tickets
(
    id          int          NOT NULL,
    created_on  timestamp default current_timestamp,
    updated_on  timestamp default current_timestamp,
    identifier  varchar(255) NOT NULL,
    ticket_type varchar(50)  NOT NULL,
    description varchar(255) NOT NULL,
    price       varchar(255) NOT NULL,
    event_id    int          NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events (id),
    constraint tickets_pk
        primary key (id)
);

create unique index tickets_identifier_uindex
    on tickets (identifier);
