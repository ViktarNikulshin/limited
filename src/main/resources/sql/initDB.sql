create table if not exists address
(
    report_id    integer not null,
    city         varchar,
    street       varchar,
    house        integer,
    building     varchar,
    flat         integer,
    entrance     integer,
    floor        integer,
    pos          varchar,
    full_address varchar,
    lat          varchar(20),
    lon          varchar(20),
    routing      integer
    );

alter table address
    owner to vnikulshin;

create table if not exists reference
(
    id   serial,
    type integer,
    name varchar
);

alter table reference
    owner to vnikulshin;

create unique index if not exists reference_id_uindex
    on reference (id);

create table if not exists users
(
    id            serial,
    login         varchar(50)                                       not null,
    password      varchar,
    email         varchar(50),
    telegram_name varchar(50),
    name          varchar(20),
    constraint users_pk
    primary key (id)
    );

alter table users
    owner to vnikulshin;

-- alter sequence users_id_seq owned by users.id;

create table if not exists report
(
    id            serial,
    mobile        text,
    prise         numeric,
    order_text    text,
    phone_number  text,
    executed      boolean default false,
    runner        integer,
    delivery_date date,
    constraint report_pkey
    primary key (id),
    constraint report___fk_runner
    foreign key (runner) references users
    );

alter table report
    owner to vnikulshin;

create unique index if not exists users_id_uindex
    on users (id);

create table if not exists roles
(
    id   serial,
    name varchar(50)
    );

alter table roles
    owner to vnikulshin;

create unique index if not exists roles_id_uindex
    on roles (id);

create table if not exists user_role
(
    user_id integer,
    role_id integer
);

alter table user_role
    owner to vnikulshin;


-- INSERT INTO public.roles (id, name) VALUES (1, 'ROLE_USER');
-- INSERT INTO public.roles (id, name) VALUES (2, 'ROLE_ADMIN');
-- INSERT INTO public.roles (id, name) VALUES (3, 'ROLE_RUNNER');
-- INSERT INTO public.roles (id, name) VALUES (4, 'ROLE_MANAGER');
--
-- INSERT INTO public.reference (id, type, name) VALUES (1, 1, 'Минск');
-- INSERT INTO public.reference (id, type, name) VALUES (2, 1, 'Сенница');
-- INSERT INTO public.reference (id, type, name) VALUES (3, 1, 'Копище');
-- INSERT INTO public.reference (id, type, name) VALUES (4, 1, 'Колодищи');
-- INSERT INTO public.reference (id, type, name) VALUES (5, 1, 'Баравляны');
-- INSERT INTO public.reference (id, type, name) VALUES (6, 4, '029');
-- INSERT INTO public.reference (id, type, name) VALUES (7, 4, '033');
-- INSERT INTO public.reference (id, type, name) VALUES (8, 4, '044');
-- INSERT INTO public.reference (id, type, name) VALUES (9, 4, '025');