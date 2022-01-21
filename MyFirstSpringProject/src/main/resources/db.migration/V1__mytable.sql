CREATE TABLE user (
id bigint primary key auto_increment,
first_name varchar(64) not null,
last_name varchar(64) not null,
username varchar(64) unique not null,
password varchar(64) not null,
email varchar(64) not null,
role varchar(64) not null
);

CREATE TABLE task (
id bigint primary key auto_increment,
creator_username varchar(64) not null,
worker_username varchar(64) not null,
name varchar(64) not null,
description varchar(64) not null,
difficulty varchar(64) not null,
status varchar(64) not null,
user_id bigint,
foreign key(user_id) references user(id)
);