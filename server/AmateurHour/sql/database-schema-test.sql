drop database if exists ah_data_test;
create database ah_data_test;
use ah_data_test;

create table app_user (
	app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    app_user_bio varchar(200),
    email varchar(50) not null unique,
    password_hash varchar(2048) not null,
    disabled bit not null default(0)
);

create table app_role (
   app_role_id int primary key auto_increment,
   `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
            references app_user(app_user_id),
    constraint fk_app_user_role_role_id
        foreign key (app_role_id)
            references app_role(app_role_id)
);

insert into app_role (`name`) values
    ('USER'),
    ('ADMIN');

create table state (
	state_id int primary key auto_increment,
    state_code varchar(2) not null unique
);

create table city (
	city_id int primary key auto_increment,
    city_name varchar(50) not null
);

create table app_event (
	event_id int primary key auto_increment,
    host_id int not null,
    event_title varchar(50) not null,
    event_capacity int not null,
    event_address varchar(100) not null,
    city_id int not null,
    state_id int not null,
    event_date datetime not null,
    event_description varchar(1000) not null,
    event_notes varchar(200),
    cancelled bit not null default(0),

    constraint fk1
		foreign key (host_id)
			references app_user(app_user_id),
	constraint fk2
		foreign key (city_id)
			references city(city_id),
	constraint fk3
		foreign key (state_id)
			references state(state_id)
);

create table rating (
	rating_id int primary key auto_increment,
    app_user_id int not null,
    event_id int not null,
    rating_comment varchar(500),
    rating_score int,
    constraint fk4
		foreign key (app_user_id)
			references app_user(app_user_id),
	constraint fk5
		foreign key (event_id)
			references app_event(event_id),
	constraint unique key uq_rating (app_user_id, event_id)
);

create table rsvp (
    app_user_id int not null,
    event_id int not null,
    constraint fk6
		foreign key (app_user_id)
			references app_user(app_user_id),
	constraint fk7
		foreign key (event_id)
			references app_event(event_id),
	constraint unique key uq_rsvp (app_user_id, event_id)
);

create table tag (
	tag_id int primary key auto_increment,
    tag_name varchar(20) not null unique
);

create table event_tag (
	tag_id int not null,
    event_id int not null,
    constraint fk8
		foreign key (tag_id)
			references tag(tag_id),
    constraint fk9
		foreign key (event_id)
			references app_event(event_id),
	constraint unique key uq_tag (tag_id, event_id)
);

insert into state(state_code)
values
('AL'),
('AK'),
('AZ'),
('AR'),
('CA'),
('CO'),
('CT'),
('DE'),
('DC'),
('FL'),
('GA'),
('HI'),
('ID'),
('IL'),
('IN'),
('IA'),
('KS'),
('KY'),
('LA'),
('ME'),
('MT'),
('NE'),
('NV'),
('NH'),
('NJ'),
('NM'),
('NY'),
('NC'),
('ND'),
('OH'),
('OK'),
('OR'),
('MD'),
('MA'),
('MI'),
('MN'),
('MS'),
('MO'),
('PA'),
('RI'),
('SC'),
('SD'),
('TN'),
('TX'),
('UT'),
('VT'),
('VA'),
('WA'),
('WV'),
('WI'),
('WY');

delimiter //
create procedure set_known_good_state()
begin

delete from app_user_role;
alter table app_user_role auto_increment = 1;

delete from rsvp;
alter table rsvp auto_increment = 1;

delete from event_tag;
alter table event_tag auto_increment = 1;

delete from rating;
alter table rating auto_increment = 1;

delete from app_event;
alter table app_event auto_increment = 1;

delete from city;
alter table city auto_increment = 1;

delete from tag;
alter table tag auto_increment = 1;

delete from app_user;
alter table app_user auto_increment = 1;

-- data
insert into app_user (username, app_user_bio, email, password_hash, disabled)
values
('Thenue','EMPTY BIO','JessicaVPerez@cuvox.de','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Suallible','EMPTY BIO','RyanPMcDole@einrot.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Conice','EMPTY BIO','JamieJArnold@rhyta.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Faider','EMPTY BIO','BrianALeak@armyspy.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Warme1964','EMPTY BIO','GeorgeAByrne@einrot.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Alievespecon67','EMPTY BIO','DianeTTaylor@armyspy.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('AmyCooks','EMPTY BIO','AmyCooks@gustr.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Whissent','EMPTY BIO','GregoryMHorton@fleckens.hue','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Coliould','EMPTY BIO','DeannaBHosking@einrot.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Onancery','EMPTY BIO','MarkMHoelscher@jourrapide.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Wend1985','EMPTY BIO','JeffreySSumlin@teleworm.us','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Sampeatent','EMPTY BIO','MatthewJCollins@jourrapide.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Whabre','EMPTY BIO','CoraGBradbury@fleckens.hue','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Iggerstoost1955','EMPTY BIO','EstelleTSoto@armyspy.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Mintwoubity','EMPTY BIO','GregMWells@gustr.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Theemence','EMPTY BIO','KathrineBBrassell@superrito.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Wonstaid1974','EMPTY BIO','ElseKMcMullen@jourrapide.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Fornelets','EMPTY BIO','ClarenceLNelson@gustr.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Mandear','EMPTY BIO','ThomasCHowe@einrot.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Onlyingthend','EMPTY BIO','KatherineKKnox@jourrapide.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Lacquit68','EMPTY BIO','JamesMSignorelli@dayrep.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Pontme','EMPTY BIO','JoshuaEMcCoy@fleckens.hulu','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Cleakettent1983','EMPTY BIO','EmmaRBishop@gustr.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Vinter54','EMPTY BIO','SamBPrimo@dayrep.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Brenceing','EMPTY BIO','LoisPConder@dayrep.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Selard85','EMPTY BIO','AmosGAkin@fleckens.hush','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Anconst','EMPTY BIO','HeatherZRarick@dayrep.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Districe','EMPTY BIO','WillieSSanders@einrot.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Hancerper','EMPTY BIO','CarterDIsrael@gustr.com','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0),
('Theaver','EMPTY BIO','MarieJSwihart@fleckens.hu','$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa',0);

insert into app_user_role
values
    (1,2),
    (2,2),
    (3,2),
    (4,1),
    (5,1),
    (6,1),
    (7,1),
    (8,1),
    (9,1),
    (10,1),
    (11,1),
    (12,1),
    (13,1),
    (14,1),
    (15,1),
    (16,1),
    (17,1),
    (18,1),
    (19,1),
    (20,1),
    (21,1),
    (22,1),
    (23,1),
    (24,1),
    (25,1),
    (26,1),
    (27,1),
    (28,1),
    (29,1),
    (30,1);

insert into city(city_name)
values
('Minneapolis'),
('St Paul'),
('Park Ridge'),
('Milwaukee');

insert into app_event(host_id, event_title, event_address, city_id, state_id, event_date, event_capacity, event_description, event_notes)
    values
(6,'Lego Building Club','128 W 33rd St','1','36','2022-06-15 17:30:00','5','I just bought the classic, massive death star! Lets hang out and put it together!',''),
(7,'Amy and friends cooking class','920 E Lake St','1','36','2022-06-15 19:00:00','15','We will be cooking sushi! BYOF (Bring your own fish). I will provide the rice and the seaweed. See you guys soon and feel free to reach out to me if you have any questions. EDIT: I recommend bringing Salmon, Tuna, and imitation crab!',''),
(4,'Woodworking stool/table workshop','218 7th St E','2','36','2022-06-17 06:00:00','5','Hey guys, come hang out and learn how to make a stool! I ask you bring about 20.00 to help cover the cost of lumber and materials but I’ve got it covered if money is tight. See you!','Event runs 6:00 am until noon'),
(7,'Bible Study','128 W 33rd St','1','36','2022-06-17 13:00:00','30','Bring the children and yourself for Bible Study, every Friday at 1:00 pm! ',''),
(4,'Basket Weaving','218 7th St E','2','36','2022-06-17 13:00:00','5','Have you ever wanted to learn basket weaving? Come see us at your local maker-space for a tutorial on this handy and fun hobby.','This event runs long, from 1:00 until 3:00 pm'),
(4,'Black Smitthing Spoons','218 7th St E','2','36','2022-06-18 17:00:00','7','Today, we will be making spoons. Big spoons. Small spoons. Maybe even a spork. ','Note: This event runs from 5:00 pm until 8:00 pm, and we request you bring a donation of $15 for tools and materials'),
(2,'Friday Night Magic','4357 Minnehaha Ave','1','36','2022-06-17 17:00:00','25','Feel the magic at Dumpster Cat Games ;)','Event runs 5 pm until 8 pm'),
(1,'Bingo','1101 W Broadway Ave','1','36','2022-06-19 06:00:00','50','BINGO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!',''),
(3,'Watch the Bucks game','3322 42nd Ave S','1','36','2022-06-19 18:00:00','10','Just moved to Minneapolis. Looking to meet new people and watch the Bucks Game.',''),
(1,'Stamp Lickers Anonymous','4301 MN-7 Suite 120','1','36','2022-06-15 18:00:00','16','Come and enjoy with others the fascinating and awesome hobby of stamp collecting! Bring your collection, trade stamps, and share the story behind your personal favorite.',''),
(6,'Rock Hounding','116 Church St SE','1','36','2022-06-18 06:00:00','16','We will be heading to the bluffs along the mississippi to hunt for trilobites this Saturday. Please bring a rock hammer, sledge, hard hats, safety glasses, gloves, water, and snacks. If you need any help with any of these items, let me know and I will see what I can get from the department to loan out. We will be taking department vans but if you would like to drive yourself, let me know. ','This event starts at 6 am and runs until around 5'),
(8,'Lakeside Tea Party','S River Rd','3','14','2022-05-22 12:00:00','20','Tea Party at the park on a bright and sunny day.',''),
(8,'Naruto run contest','S River Rd','3','14','2022-06-13 15:00:00','30','Gotta go fast! Contest to see who’s the best ninja. ','Bring your own headbands.');

 -- NOTE: These ratings are only for 2 clubs ATM
insert into rating (app_user_id, event_id, rating_comment, rating_score)
value
(10,1,"I'm a construction worker by profession and I love playing with Legos. My son loves to build things like airplanes, cars, trains and more. Playing with Legos is the perfect balance of father-son time for us.", 10),
(12,1,"I'm a member of the Lego Club and it's really awesome. I love all the adventures and challenges we get to go on with my fellow members. I recommend joining. You won't regret it!",10),
(11,10,"I joined the Stamp Collecting Club about 2 months ago. The first couple months were great, I met some really nice people and was getting into the hobby. Nowadays though it's all seniors, which is not that much fun. All they ever talk about are their old stamps or other collectible baseball cards which isn't really my thing.",6),
(13,10,"I love stamp collecting and I've been a member of the club for 10 years.",10),
(12,10,"I don’t think this is really a stamp collecting club anymore.",2),
(14,10,"The club was originally recommended by a friend, and had seriously changed my life. It's easy, fun, and I can't believe all the stamps I have collected over the years!", 9),
(15,10,"Had the best time with my stamp collecting club last night. We usually have the same old event where we just hang out in a circle and trade stamps but last night we decided to trade a few baseball cards. It was such a success! I think we were all thinking of how much fun it would be to do that every time.", 10),
(16,10,"I've been stamp collecting since I was a little kid and it always amazes me to see all the fascinating new stamps. I love going to meetings because we always have such good conversations about our stamps.",10),
(17,10,"The stamp club is great! It has helped me to catalog my collection, identify stamps from all around the world, and make up my mind about any purchases I need to make.",8);

insert into rsvp (event_id, app_user_id)
values
(6,10),
(6,12),
(6,19),
(1,13),
(1,14),
(1,15),
(1,16),
(1,17);

insert into tag (tag_name)
values
('Wood Working'),
('Metal Working'),
('Pottery'),
('Book Club'),
('Collecting'),
('Cards/ Games'),
('Cooking'),
('Hanging out'),
('Group Exercises'),
('Study'),
('Other');

insert into event_tag (event_id, tag_id)
values
(1,11),
(2,7),
(3,1),
(4,10),
(5,11),
(6,2),
(7,6),
(8,6),
(9,6),
(10,5),
(11,5),
(12,8),
(13,9),
(6,5),
(6,8),
(1,8),
(9,9),
(9,8),
(11,8),
(11,10),
(8,8),
(12,7);

end //
-- 4. Change the statement terminator back to the original.
delimiter ;