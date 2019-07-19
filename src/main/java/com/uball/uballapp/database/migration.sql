use uball_db;


/* create and seed users table */

CREATE TABLE `users` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `dob` datetime NOT NULL,
                         `email` varchar(255) NOT NULL,
                         `first_name` varchar(255) NOT NULL,
                         `gender` char(1) NOT NULL,
                         `is_admin` bit(1) NOT NULL,
                         `last_name` varchar(255) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `points` bigint(20) NOT NULL,
                         `username` varchar(255) NOT NULL,
                         `league_id` bigint(20) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY (`email`),
                         KEY (`league_id`),
                         CONSTRAINT FOREIGN KEY (`league_id`) REFERENCES `leagues` (`id`)
);

insert into users
(
    id,
    dob,
    email,
    first_name,
    gender,
    is_admin,
    last_name,
    password,
    points,
    username,
    league_id
)
values (1,
        '2001-01-01',
        'youremail@email.com',
        'admin',
        'F',
        true,
        'admin',
        '$2a$10$/9l0fOthk0l/2l1taiOrouGqJ15kIK/biDg1GxaaxhnIYT9/fGWrK',
        0,
        'admin',
        1
       );

/* ^ hashed password = 'password' ^ */




/* create and seed groups table */

CREATE TABLE `groups` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) NOT NULL,
                          PRIMARY KEY (`id`)
);

insert into `groups`
(
    id,
    name
)
values (1,
        'group 1'
       );




/* create and seed leagues table */

CREATE TABLE `leagues` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `name` varchar(255) NOT NULL,
                           PRIMARY KEY (`id`)
);

insert into leagues
(
    id,
    name
)
values (1,
        'League 1'
       ),
       (2,
        'League 2');



/* create and seed machines table */

CREATE TABLE `machines` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `name` varchar(255) NOT NULL,
                            PRIMARY KEY (`id`)
);

insert into machines
(
    id,
    name
)
values (1,
        'Machine 1'
       ),
       (2,
        'Machine 2'
       ),
       (3,
        'Machine 3'
       ),
       (4,
        'Machine 4'
       ),
       (5,
        'Machine 5'
       ),
       (6,
        'Machine 6'
       ),
       (7,
        'Machine 7'
       ),
       (8,
        'Machine 8'
       ),
       (9,
        'Machine 9'
       ),
       (10,
        'Machine 10'
       );



/* create and seed scores table */

CREATE TABLE `scores` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `date` datetime NOT NULL,
                          `score` bigint(20) NOT NULL,
                          `machine_id` bigint(20) DEFAULT NULL,
                          `user_id` bigint(20) DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY (`machine_id`),
                          KEY (`user_id`),
                          CONSTRAINT FOREIGN KEY (`machine_id`) REFERENCES `machines` (`id`),
                          CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

insert into scores
(
    id,
    date,
    score,
    machine_id,
    user_id
)
values (1,
        '2019-01-01',
        980000,
        1,
        1
       ),
       (2,
        '2019-01-01',
        980000,
        2,
        1
       ),
       (3,
        '2019-01-01',
        980000,
        3,
        1
       );