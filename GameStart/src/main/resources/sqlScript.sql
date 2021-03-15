drop table if exists game_platform;
drop table if exists platform;
drop table if exists game_developer;
drop table if exists developer;
drop table if exists game_genre;
drop table if exists genre;
drop table if exists favorite;
drop table if exists review;

drop table if exists game_publisher;
drop table if exists game;
drop table if exists publisher;
drop table if exists app_user;



create table app_user(
    id serial,
    first_name varchar(25) not null,
    last_name varchar(25) not null,
    username varchar(25) not null Unique,
    password varchar(25) not null,
    email varchar(256) not null Unique,
    role_name varchar(25) not null default 'Basic',


    constraint user_PK primary key (id)
);


create table publisher (
    id        serial,
    name    varchar(25) not null,

    constraint publisher_pk
    primary key (id)
);

create table game(
    id            	serial,
    name            varchar not null,
    description     text,
    rating          int default -1,
    slug			varchar,
    background_image	varchar,

    constraint game_pk
    primary key (id)
);

create table game_publisher(
    game_id int,
    publisher_id int,

    constraint game_publisher_PK primary key (game_id,publisher_id),
    constraint game_publisher_id_FK foreign key (publisher_id) references publisher,
    constraint game_publisher_game_id_FK foreign key (game_id) references game

);



create table review(
    description text default null,
    score int not null,
    game_id int not null,
    creator_id int not null,

    constraint review_id_PK primary key (game_id,creator_id),
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

create table genre (
	id		serial,
	name	varchar(25) not null,

    constraint genre_pk
    primary key (id)
);

create table game_genre(
	game_id		int,
	genre_id	int,

	constraint game_genre_pk
    primary key (game_id, genre_id),

    constraint game_id_fk
    foreign key (game_id)
    references game,

    constraint genre_id_fk
    foreign key (genre_id)
    references genre
);

--Inserts

--app_user Table
--Calvin
insert into app_user(first_name,last_name,username,password,email) values ('Calvin','Zheng','calvin123','password','calvin123@yahoo.com');
--Daniel
insert into app_user(first_name,last_name,username,password,email) values ('Daniel','Skwarcha','daniel123','password','daniel123@yahoo.com');
--Eli
insert into app_user(first_name,last_name,username,password,email) values ('Eli','Corpron','eli123','password','eli123@yahoo.com');


--publisher Table
--valheim
insert into publisher(name) values ('Coffee Stain Publishing');
--GTA 5
insert into publisher(name) values ('Rockstar Games');
--Rust
insert into publisher(name) values ('Facepunch Studios');


--game Table
insert into game(name,description)
    values ('Valheim','A brutal exploration and survival game for 1-10 players, set in a procedurally-generated purgatory inspired by viking culture. Battle, build, and conquer your way to a saga worthy of Odin’s patronage!');

insert into game(name,description)
    values ('Grand Theft Auto V', 'Grand Theft Auto V for PC offers players the option to explore the award-winning world of Los Santos and Blaine County in resolutions of up to 4k and beyond, as well as the chance to experience the game running at 60 frames per second.');

insert into game(name,description)
    values ('Rust','The only aim in Rust is to survive - Overcome struggles such as hunger, thirst and cold. Build a fire. Build a shelter. Kill animals. Protect yourself from other players.');


--genre Table
--valheim and Rust
insert into genre(name) values ('Open World Survival Craft');
insert into genre(name) values ('Online Co-Op');
insert into genre(name) values ('Survival');

--GTA 5
insert into genre(name) values ('Open World');
insert into genre(name) values ('Action');
insert into genre(name) values ('Multiplayer');

--game_genre
--valheim
insert into game_genre(game_id,genre_id) values (1,1);
insert into game_genre(game_id,genre_id) values (1,2);
insert into game_genre(game_id,genre_id) values (1,3);

--GTA 5
insert into game_genre(game_id,genre_id) values (2,4);
insert into game_genre(game_id,genre_id) values (2,5);
insert into game_genre(game_id,genre_id) values (2,6);

--Rust
insert into game_genre(game_id,genre_id) values (3,1);
insert into game_genre(game_id,genre_id) values (3,2);
insert into game_genre(game_id,genre_id) values (3,3);



--review Table
--Game 1 Valheim
insert into review (description,score,game_id,creator_id)
    values ('Great Game',5,1,1);

insert into review (description, score, game_id, creator_id)
    values ('Decent. Could be better',3,1,2);

insert into review (description, score, game_id, creator_id)
    values ('Worst game I have ever played',1,1,3);

--Game 2 GTA V
insert into review (description,score,game_id,creator_id)
    values ('Great Game',5,2,1);

insert into review (description, score, game_id, creator_id)
    values ('Decent. Could be better',3,2,2);

insert into review (description, score, game_id, creator_id)
    values ('Worst game I have ever played',1,2,3);

--Game 3 Rust
insert into review (description,score,game_id,creator_id)
    values ('Great Game',5,3,1);

insert into review (description, score, game_id, creator_id)
    values ('Decent. Could be better',3,3,2);

insert into review (description, score, game_id, creator_id)
    values ('Worst game I have ever played',1,3,3);


--Game Publisher Table

insert into game_publisher(game_id,publisher_id) values (1,1);

insert into game_publisher(game_id,publisher_id) values (2,2);

insert into game_publisher(game_id,publisher_id) values (3,3);

--favorite Table
--user 1 Calvin
insert into favorite (game_id,user_id) values (1,1);
insert into favorite (game_id,user_id) values (2,1);
insert into favorite (game_id,user_id) values (3,1);

--user 2 Daniel
insert into favorite (game_id,user_id) values (1,2);
insert into favorite (game_id,user_id) values (2,2);
insert into favorite (game_id,user_id) values (3,2);

--user 3 Eli
insert into favorite (game_id,user_id) values (1,3);
insert into favorite (game_id,user_id) values (2,3);
insert into favorite (game_id,user_id) values (3,3);


--developer Table
--valheim
insert into developer(name) values ('Iron Gate AB');
--GTA 5
insert into developer(name) values ('Rockstar North');
--Rust
insert into developer(name) values ('Facepunch Studios');


--game_developer Table
--valheim
insert into game_developer(game_id, developer_id) values (1,1);
--GTA 5
insert into game_developer(game_id, developer_id) values (2,2);
--Rust
insert into game_developer(game_id, developer_id) values (3,3);


--platform Table
insert into platform(name) values ('PC');
insert into platform(name) values ('XBOX');
insert into platform(name) values ('Playstation');


--game_platform Table
--valheim
insert into game_platform (game_id,platform_id) values (1,1);
--GTA 5
insert into game_platform (game_id,platform_id) values (2,1);
insert into game_platform (game_id,platform_id) values (2,2);
insert into game_platform (game_id,platform_id) values (2,3);
--Rust
insert into game_platform (game_id,platform_id) values (3,1);
insert into game_platform (game_id,platform_id) values (3,2);
insert into game_platform (game_id,platform_id) values (3,3);



--Selects

--get all app users
select * from app_user au;

--get all reviews
select * from review;

--get all user reviews with their firstname,lastname, game name, and rating
select g.name, r.score,u.first_name , u.last_name from app_user u, review r, game g
		where r.creator_id = u.id and r.game_id = g.id ;

--get all games
select * from game;

--get all games with genre
select g.name,  array_to_string( array_agg(distinct gen."name"),',') as Genre from game g, genre gen, game_genre gg
	where g.id = gg.game_id and gen.id = gg.genre_id
	group by g."name" ;

--get Game with publisher, developer, and platform(s)
select g.name as GAME, p."name" as Publisher, d."name" as Developer ,array_to_string( array_agg(distinct plat."name"),',') as platform
	from game g, developer d, publisher p, platform plat, game_developer gd , game_platform gp, game_publisher gpub
	where gd.developer_id = d.id and gd.game_id = g.id and gp.game_id = g.id and gp.platform_id = plat.id and gpub.game_id = g.id and gpub.publisher_id = p.id
	group by g.name, p."name",d."name" ;