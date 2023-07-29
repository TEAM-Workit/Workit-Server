drop table if exists work_ability;
drop table if exists work;
drop table if exists project;
drop table if exists ability;
drop table if exists users;

create table ability
(
    ability_id   bigint generated by default as identity
        primary key,
    created_at   timestamp DEFAULT CURRENT_TIMESTAMP(),
    modified_at  timestamp DEFAULT CURRENT_TIMESTAMP(),
    ability_type varchar(10),
    name         varchar(30)
);

create table users
(
    user_id     bigint generated by default as identity
        primary key,
    created_at  timestamp DEFAULT CURRENT_TIMESTAMP(),
    modified_at timestamp DEFAULT CURRENT_TIMESTAMP(),
    email       varchar(50),
    nickname    varchar(20),
    social_type varchar(10),
    is_deleted boolean DEFAULT false,
    delete_reason   varchar(250),
    social_id   varchar(255)
);

create table project
(
    project_id  bigint generated by default as identity
        primary key,
    created_at  timestamp DEFAULT CURRENT_TIMESTAMP(),
    modified_at timestamp DEFAULT CURRENT_TIMESTAMP(),
    title       varchar(30),
    user_id     bigint,
    foreign key (user_id) references users(user_id) on delete cascade
);

create table work
(
    work_id     bigint generated by default as identity
        primary key,
    created_at  timestamp DEFAULT CURRENT_TIMESTAMP(),
    modified_at timestamp DEFAULT CURRENT_TIMESTAMP(),
    date        timestamp,
    description text,
    title       varchar(60),
    project_id  bigint,
    foreign key (project_id) references project(project_id) on delete cascade
);

create table work_ability
(
    work_ability_id bigint generated by default as identity
        primary key,
    created_at      timestamp DEFAULT CURRENT_TIMESTAMP(),
    modified_at     timestamp DEFAULT CURRENT_TIMESTAMP(),
    ability_id      bigint,
    work_id         bigint,
    foreign key (ability_id) references ability(ability_id) on delete cascade,
    foreign key (work_id) references work(work_id) on delete cascade
);