drop table if exists game_platform;
drop table if exists platform;
drop table if exists game_developer;
drop table if exists developer;
drop table if exists favorite;
drop table if exists review;
drop table if exists game;
drop table if exists publisher;
drop table if exists app_user;
drop table if exists user_role;

CREATE TABLE user_role(
    id            serial,
    role_name     varchar(35) not null,

    constraint user_role_pk
    primary key (id)
);

create table app_user(
    id serial,
    first_name varchar(25) not null,
    last_name varchar(25) not null,
    username varchar(25) not null Unique,
    password varchar(25) not null,
    email varchar(256) not null Unique,
    role_id int not null,

    constraint user_PK primary key (id) ,
    constraint user_role_FK foreign key (role_id) references user_role
);

create table publisher (
    id        serial,
    name    varchar(25) not null,

    constraint publisher_pk
    primary key (id)
);

create table game(
    id            	serial,
    name            varchar(50) not null,
    genre           varchar(25) not null,
    description     text,
    rating          int default -1,
    publisher_id    int not null,

    constraint game_pk
    primary key (id),

    constraint publisher_id_fk
    foreign key (publisher_id)
    references publisher 


);

create table review(
    id serial,
    description text default null,
    score int not null,
    game_id int not null,
    creator_id int not null,

    constraint review_id_PK primary key (id),
    constraint creator_id_FK foreign key (creator_id) references app_user,
    constraint game_id_FK foreign key (game_id) references game

);

create table favorite(
    game_id int ,
    user_id int ,

    constraint favorite_id_PK primary key (game_id,user_id),
    constraint favorite_game_id foreign key (game_id) references game, 
    constraint favorite_user_id foreign key (user_id) references app_user
); 

create table developer(
    id serial,
    name varchar(25) not null,

    constraint developer_id_PK primary key (id)
);

create table game_developer(
    game_id int,
    developer_id int,

    constraint game_developer_PK primary key (game_id,developer_id),
    constraint game_developer_id_FK foreign key (developer_id) references developer,
    constraint game_developer_game_id_FK foreign key (game_id) references game
);


create table platform (
    id        serial,
    name    varchar(25) not null,

    constraint platform_pk
    primary key (id)
);

create table game_platform (
    game_id            int,
    platform_id        int,

    constraint game_platform_pk
    primary key (game_id, platform_id),

    constraint game_id_fk
    foreign key (game_id)
    references game,

    constraint platform_id_fk
    foreign key (platform_id)
    references platform

);


