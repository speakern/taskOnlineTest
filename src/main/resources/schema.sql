DROP INDEX ix_auth_username IF EXISTS;
DROP INDEX ix_auth_user_role IF EXISTS;
DROP TABLE authorities IF EXISTS;
DROP TABLE User_Answers IF EXISTS;
DROP TABLE Answers IF EXISTS;
DROP TABLE Queries IF EXISTS;
DROP TABLE users IF EXISTS;


CREATE TABLE Queries(
  id INT AUTO_INCREMENT PRIMARY KEY,
  text VARCHAR(100) NOT NULL,
  type INT NOT NULL,
  right_answer INT NOT NULL
);

CREATE TABLE Answers (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  text VARCHAR(100) NOT NULL,
  query_num INT NOT NULL,
  query_id  INT NOT NULL,
    foreign key (query_id) references Queries(id)
);

CREATE TABLE users(
  id INT AUTO_INCREMENT PRIMARY KEY,
	username varchar(50) not null,
	password varchar(100) not null,
	enabled boolean not null
);
CREATE UNIQUE index ix_auth_username on users (username);

CREATE TABLE User_Answers (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  query_id INT NOT NULL,
  answer VARCHAR(100) NOT NULL,
  is_correct INT NOT NULL,
    foreign key (query_id) references Queries(id),
    foreign key(user_id) references users(id)
);

CREATE TABLE authorities (
	user_id INT NOT NULL,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(user_id) references users(id)
);
CREATE UNIQUE index ix_auth_user_role on authorities (user_id,authority);