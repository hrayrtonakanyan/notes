create database notes;

create table users
(
    user_id    bigserial               not null
        constraint users_pk
            primary key,
    email      varchar(50)             not null,
    password   varchar(255)            not null,
    created_at timestamp default now() not null,
    updated_at timestamp
);

create unique index users_email_uindex
    on users (email);

create table user_notes
(
    user_note_id bigserial               not null
        constraint user_notes_pk
            primary key,
    user_id      bigint                  not null
        constraint user_notes_users_user_id_fk
            references users
            on update cascade on delete cascade,
    title        varchar(50)             not null,
    note         text,
    created_at   timestamp default now() not null,
    updated_at   timestamp
);

create index user_notes_user_id_index
    on user_notes (user_id);
create unique index user_notes_user_note_id_uindex
    on user_notes (user_note_id);