create table IF NOT EXISTS report
(
	 id           serial not null
        constraint report_pkey
            primary key,
    mobile       text,
    prise        numeric,
    order_text   text,
    phone_number text
);
create table IF NOT EXISTS address
(
     report_id  integer
        constraint address_report_id_fk
            references report,
    city     varchar,
    street   varchar,
    house    integer,
    building varchar,
    flat     integer,
    entrance integer,
    floor    integer
);
