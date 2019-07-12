

use demo_capstone_DB;

CREATE TABLE IF NOT EXISTS leagues(
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS machines(
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS 'groups'(
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users(
  id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password Varchar(255) NOT NULL,
  dob DATE NOT NULL ,
  gender CHAR NOT NULL,
  is_admin BOOLEAN,
  league_id INT NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY (league_id) REFERENCES leagues(id)
);

CREATE TABLE IF NOT EXISTS scores(
  id INT NOT NULL AUTO_INCREMENT,
  score INT NOT NULL,
  date DATE NOT NULL ,
  user_id INT NOT NULL,
  machine_id INT NOT NULL,
  PRIMARY KEY(id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (machine_id) REFERENCES machines(id)
);

CREATE TABLE IF NOT EXISTS user_groups (
 user_id INT NOT NULL,
 group_id INT NOT NULL,
 FOREIGN KEY (group_id) REFERENCES `groups`(id),
 FOREIGN KEY (user_id) REFERENCES `users`(id)
);

