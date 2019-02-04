PRAGMA FOREIGN_KEYS = ON;

CREATE TABLE Administrator (
  id INTEGER PRIMARY KEY,
  username TEXT UNIQUE,
  password TEXT 
);

INSERT INTO Administrator VALUES ( 1, 'admin', 'admin' );

CREATE TABLE Login (
  id INTEGER PRIMARY KEY ,
  user TEXT ,
  time TEXT 
);

CREATE TABLE Continent (
  id INTEGER PRIMARY KEY ,
  name TEXT
);

INSERT INTO Continent VALUES (1,'Europe');
INSERT INTO Continent VALUES (2,'Asia');
INSERT INTO Continent VALUES (3,'Africa');
INSERT INTO Continent VALUES (4,'North America');
INSERT INTO Continent VALUES (5,'South America');
INSERT INTO Continent VALUES (6,'Antarctica');
INSERT INTO Continent VALUES (7,'Oceania');

CREATE TABLE Country (
  id INTEGER PRIMARY KEY ,
  name TEXT ,
  continent INTEGER REFERENCES Continent(id) 
);

INSERT INTO Country VALUES ( 1, 'Germany', 1 );
INSERT INTO Country VALUES ( 2, 'France', 1 );
INSERT INTO Country VALUES ( 3, 'Sudan', 3 );
INSERT INTO Country VALUES ( 4, 'Indonesia', 2 );
INSERT INTO Country VALUES ( 5, 'Costa Rica', 4 );

CREATE TABLE City (
  id INTEGER PRIMARY KEY ,
  name TEXT ,
  country INTEGER REFERENCES  Country(id) ON DELETE SET NULL
);

INSERT INTO City VALUES ( 1, 'Nice', 2 );
INSERT INTO City VALUES ( 2, 'Berlin', 1 );
INSERT INTO City VALUES ( 3, 'München', 1 );
INSERT INTO City VALUES ( 4, 'Jakarta', 4 );
INSERT INTO City VALUES ( 5, 'Khartoum', 3 );

CREATE TABLE Location (
  id INTEGER PRIMARY KEY ,
  postal_code TEXT ,
  street_adress TEXT ,
  city INTEGER REFERENCES City(id) ON DELETE SET NULL
);

INSERT INTO Location VALUES ( 1, '12623', 'Bruno-Baum-Straße', 2 );
INSERT INTO Location VALUES ( 2, '12623', 'Poelchaustraße', 2 );
INSERT INTO Location VALUES ( 3, '06300', 'Rue Torrini', 1 );
INSERT INTO Location VALUES ( 4, '12623', 'Jl. Dempo', 4 );
INSERT INTO Location VALUES ( 5, '12623', 'Jl. Mendut', 4 );

CREATE TABLE Job (
  id INTEGER PRIMARY KEY ,
  job_title TEXT 
);

INSERT INTO Job VALUES ( 1, 'Secretary' );
INSERT INTO Job VALUES ( 2, 'IT Programmer' );
INSERT INTO Job VALUES ( 3, 'Director' );
INSERT INTO Job VALUES ( 4, 'Marketing Manager' );
INSERT INTO Job VALUES ( 5, 'Patent Agent' );

CREATE TABLE Department (
  id INTEGER PRIMARY KEY ,
  name TEXT ,
  location INTEGER REFERENCES Location(id) ON DELETE SET NULL
);

INSERT INTO Department VALUES ( 1, 'IT Department', 1 );
INSERT INTO Department VALUES ( 2, 'Management Department', 1 );
INSERT INTO Department VALUES ( 3, 'Finance Department', 2 );
INSERT INTO Department VALUES ( 4, 'Production Department', 2 );
INSERT INTO Department VALUES ( 5, 'Administration Department', 4 );

CREATE TABLE Employee (
  id INTEGER PRIMARY KEY ,
  first_name TEXT ,
  last_name TEXT ,
  parent_name TEXT ,
  birth_date DATE ,
  umcn TEXT  UNIQUE ,
  mobile_number TEXT  UNIQUE,
  email_address TEXT UNIQUE,
  credit_card TEXT  UNIQUE,
  salary INTEGER ,
  photo TEXT NULL,
  location INTEGER REFERENCES Location(id) ON DELETE SET NULL,
  department INTEGER REFERENCES Department(id) ON DELETE SET NULL,
  job INTEGER REFERENCES Job(id) ON DELETE SET NULL
);

INSERT INTO Employee VALUES ( 1, 'Faris', 'Owner', 'Dzebrail','1998-08-24 00:00:00.000','2408998170040','060/338-1032','sisicfaris@hotmail.com','77769777123',10000,NULL,3,5,3 );
INSERT INTO Employee VALUES ( 2, 'Mirza', 'Sinanovic', 'Narcis', '1998-09-09 00:00:00.000','0909998170026','061/580-789','micki-91@hotmail.com','1231230991',5000,NULL,5,1,2 );
INSERT INTO Employee VALUES ( 3, 'Ajla', 'Salispahic', 'Rusmir', '1999-01-01 00:00:00.000','0101999175016','062/572-168','aj-la-s@hotmail.com','199901234567',5000,NULL,5,1,2 );
INSERT INTO Employee VALUES ( 4, 'Julian', 'Cutthroat', 'Jack','1997-07-07 00:00:00.000','0707997170942','060/123-4567','juli69@outlook.com','987650002',3500,NULL,4,3,4 );
INSERT INTO Employee VALUES ( 5, 'Jane', 'Doe', 'John', '1997-01-01 00:00:00.000','0101970123432','063/333-333','janedoe@outlook.com','00042011169',4000,NULL,1,5,1 );

ALTER TABLE Employee ADD manager INTEGER REFERENCES Employee(id) ON DELETE SET NULL;

UPDATE  Employee SET manager = 1 WHERE id = 2;
UPDATE  Employee SET manager = 1 WHERE id = 3;
UPDATE  Employee SET manager = 1 WHERE id = 4;
UPDATE  Employee SET manager = 1 WHERE id = 5;

ALTER TABLE Department ADD manager INTEGER REFERENCES Employee(id) ON DELETE SET NULL;

UPDATE Department SET manager = 1 WHERE id = 3;
UPDATE Department SET manager = 1 WHERE id = 5;
UPDATE Department SET manager = 2 WHERE id = 1;
UPDATE Department SET manager = 2 WHERE id = 4;
UPDATE Department SET manager = 5 WHERE id = 2;

CREATE TABLE Contract (
  id INTEGER PRIMARY KEY ,
  contract_number TEXT  UNIQUE,
  start_date DATE  ,
  end_date DATE  ,
  job INTEGER ,
  employee INTEGER
);

INSERT INTO Contract VALUES ( 1, 'A123-11195','2019-01-01 00:00:00.000','2029-01-01 00:00:00.000',1,5 );
INSERT INTO Contract VALUES ( 2, 'A123-11196','2019-02-01 00:00:00.000','2024-02-01 00:00:00.000',4,4 );
INSERT INTO Contract VALUES ( 3, 'C123-12311','2019-02-01 00:00:00.000','2024-02-01 00:00:00.000',2,2 );
INSERT INTO Contract VALUES ( 4, 'B127-34112','2019-02-01 00:00:00.000','2024-02-01 00:00:00.000',2,3 );



