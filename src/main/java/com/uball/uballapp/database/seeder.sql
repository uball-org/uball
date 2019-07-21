select * from users;


insert into leagues
(
    id,
    name
)
values (1,
        'SAPL'
       ),
       (2,
        'Belles');


insert into machines
(
    id,
    name
)
values (1,
        'The Addams Family'
       ),
       (2,
        'Atlantis'
       ),
       (3,
        'Black Hole'
       ),
       (4,
        'Deadpool'
       ),
       (5,
        'High Speed'
       ),
       (6,
        'Iron Maiden'
       ),
       (7,
        'Laser Cue'
       ),
       (8,
        'Pin-Bot'
       ),
       (9,
        'The Party Zone'
       ),
       (10,
        'Star Trek TNG'
       );


insert into groups
(
    id,
    name
)
values (1,
        'Group 1'
       ),
       (2,
        'Group 2'
       ),
       (3,
        'Group 3'
       ),
       (4,
        'Group 4'
       ),
       (5,
        'Group 5'
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
        25000,
        2,
        1
       ),
       (3,
        '2019-01-01',
        1690000,
        3,
        1
       );