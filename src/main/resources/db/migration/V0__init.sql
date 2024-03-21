create table users
(
    user_id     varchar(255) not null primary key,
    created_at  timestamp,
    updated_at  timestamp,
    birth_date  date         not null,
    description varchar(255) not null,
    gender      varchar(255) not null,
    height      varchar(255) not null,
    weight      varchar(255) not null,
    nickname    varchar(255) unique,
    password    varchar(255) not null
);

create table user_notification
(
    user_id    varchar(255) not null primary key references users (user_id),
    created_at timestamp,
    updated_at timestamp,
    is_agreed  boolean,
    token      varchar(255)
);

create table my_group
(
    id             bigserial primary key,
    created_at     timestamp,
    updated_at     timestamp,
    description    varchar(255),
    group_status   varchar(255),
    max_users      integer not null,
    meet_date      timestamp,
    meet_place     varchar(255),
    title          varchar(255),
    leader_user_id varchar(255) references users (user_id)
);

create table party
(
    id           bigserial
        primary key,
    created_at   timestamp,
    updated_at   timestamp,
    party_status varchar(255),
    group_id     bigint references my_group (id)
);

create table party_member
(
    id         bigserial
        primary key,
    created_at timestamp,
    updated_at timestamp,
    party_id   bigint references party (id),
    user_id    varchar(255) references users (user_id)
);

create table email_verification
(
    email         varchar(255) not null
        primary key,
    created_at    timestamp,
    updated_at    timestamp,
    random_number varchar(255),
    is_verified   boolean default false
);

create table chat_message
(
    id         bigserial
        primary key,
    created_at timestamp,
    updated_at timestamp,
    content    varchar(255) not null,
    group_id   bigint references my_group (id),
    user_id    varchar(255) references users (user_id)
);

create table banned_users
(
    id    bigserial
        primary key,
    email varchar(255) not null unique
);

create table deleted_group
(
    id                      bigserial primary key,
    created_at              timestamp,
    updated_at              timestamp,
    description             varchar(255) not null,
    group_status            varchar(255) not null,
    max_users               integer      not null,
    meet_date               timestamp,
    meet_place              varchar(255),
    title                   varchar(255) not null,
    user_count_when_deleted integer      not null
);

create table deleted_user
(
    id         bigserial
        primary key,
    created_at timestamp,
    updated_at timestamp,
    email      varchar(255) not null
);

create table last_read_chat_message
(
    id              bigserial
        primary key,
    chat_message_id bigint references chat_message (id),
    group_id        bigint references my_group (id),
    user_id         varchar(255) references users (user_id)
);

create table refresh_token
(
    user_id       varchar(255) not null
        primary key,
    created_at    timestamp,
    updated_at    timestamp,
    refresh_token varchar(255)
);

create table report
(
    id                    bigserial
        primary key,
    content               varchar(255) not null,
    report_target_user_id varchar(255) not null,
    reporter_user_id      varchar(255) not null
);

create table user_block_list
(
    id                     bigserial
        primary key,
    created_at             timestamp,
    updated_at             timestamp,
    blocked_target_user_id varchar(255) references users (user_id),
    user_id                varchar(255) references users (user_id)
);

create unique index user_block_list_user_id_block_target_user_id_uindex on user_block_list (blocked_target_user_id, user_id);
create unique index last_read_chat_message_user_id_group_id_uindex on last_read_chat_message (user_id, group_id);