CREATE SEQUENCE administrator_sequence
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

CREATE TABLE Administrator (
  id INTEGER PRIMARY KEY,
  username VARCHAR(30) UNIQUE,
  password VARCHAR(30) NOT NULL
);

INSERT INTO Administrator VALUES ( administrator_sequence.nextval, 'admin', 'admin' );

CREATE TABLE Continent (
  id INTEGER PRIMARY KEY ,
  name VARCHAR(50)
);

INSERT INTO Continent VALUES (1,'Europe');
INSERT INTO Continent VALUES (2,'Asia');
INSERT INTO Continent VALUES (3,'Africa');
INSERT INTO Continent VALUES (4,'North America');
INSERT INTO Continent VALUES (5,'South America');
INSERT INTO Continent VALUES (6,'Antarctica');
INSERT INTO Continent VALUES (7,'Oceania');

CREATE SEQUENCE country_sequence
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

CREATE TABLE Country (
  id INTEGER PRIMARY KEY ,
  name VARCHAR(255) NOT NULL,
  continent INTEGER REFERENCES Continent(id) NOT NULL
);

INSERT INTO Country VALUES ( country_sequence.nextval, 'Germany', 1 );
INSERT INTO Country VALUES ( country_sequence.nextval, 'France', 1 );
INSERT INTO Country VALUES ( country_sequence.nextval, 'Sudan', 3 );
INSERT INTO Country VALUES ( country_sequence.nextval, 'Indonesia', 2 );
INSERT INTO Country VALUES ( country_sequence.nextval, 'Costa Rica', 4 );

CREATE SEQUENCE city_sequence
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

CREATE TABLE City (
  id INTEGER PRIMARY KEY ,
  name VARCHAR(255) NOT NULL,
  country INTEGER REFERENCES  Country(id) ON DELETE CASCADE NOT NULL
);

INSERT INTO City VALUES ( city_sequence.nextval, 'Nice', 2 );
INSERT INTO City VALUES ( city_sequence.nextval, 'Berlin', 1 );
INSERT INTO City VALUES ( city_sequence.nextval, 'München', 1 );
INSERT INTO City VALUES ( city_sequence.nextval, 'Jakarta', 4 );
INSERT INTO City VALUES ( city_sequence.nextval, 'Khartoum', 3 );

CREATE SEQUENCE location_sequence
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

CREATE TABLE Location (
  id INTEGER PRIMARY KEY ,
  postal_code VARCHAR(255) NOT NULL,
  street_adress VARCHAR(255) NOT NULL,
  city INTEGER REFERENCES City(id) ON DELETE CASCADE NOT NULL
);

INSERT INTO Location VALUES ( location_sequence.nextval, '12623', 'Bruno-Baum-Straße', 2 );
INSERT INTO Location VALUES ( location_sequence.nextval, '12623', 'Poelchaustraße', 2 );
INSERT INTO Location VALUES ( location_sequence.nextval, '06300', 'Rue Torrini', 1 );
INSERT INTO Location VALUES ( location_sequence.nextval, '12623', 'Jl. Dempo', 4 );
INSERT INTO Location VALUES ( location_sequence.nextval, '12623', 'Jl. Mendut', 4 );

CREATE SEQUENCE job_sequence
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

CREATE TABLE Job (
  id INTEGER PRIMARY KEY ,
  job_title VARCHAR(255) NOT NULL
);

INSERT INTO Job VALUES ( job_sequence.nextval, 'Secretary' );
INSERT INTO Job VALUES ( job_sequence.nextval, 'IT Programmer' );
INSERT INTO Job VALUES ( job_sequence.nextval, 'Director' );
INSERT INTO Job VALUES ( job_sequence.nextval, 'Marketing Manager' );
INSERT INTO Job VALUES ( job_sequence.nextval, 'Patent Agent' );

CREATE SEQUENCE department_sequence
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

CREATE TABLE Department (
  id INTEGER PRIMARY KEY ,
  name VARCHAR(255) NOT NULL,
  location INTEGER REFERENCES Location(id) ON DELETE CASCADE NOT NULL
);

INSERT INTO Department VALUES ( department_sequence.nextval, 'IT Department', 1 );
INSERT INTO Department VALUES ( department_sequence.nextval, 'Management Department', 1 );
INSERT INTO Department VALUES ( department_sequence.nextval, 'Finance Department', 2 );
INSERT INTO Department VALUES ( department_sequence.nextval, 'Production Department', 2 );
INSERT INTO Department VALUES ( department_sequence.nextval, 'Administration Department', 4 );

CREATE SEQUENCE employee_sequence
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

CREATE TABLE Employee (
  id INTEGER PRIMARY KEY ,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  parent_name VARCHAR(255) NOT NULL,
  birth_date DATE NOT NULL,
  umcn VARCHAR(255) NOT NULL UNIQUE ,
  mobile_number VARCHAR(255) NOT NULL UNIQUE,
  email_adress VARCHAR(255) UNIQUE,
  credit_card VARCHAR(255) NOT NULL UNIQUE,
  salary INTEGER NOT NULL,
  photo VARCHAR(255) NULL,
  location INTEGER REFERENCES Location(id) NULL,
  department INTEGER REFERENCES Department(id) NULL,
  job INTEGER REFERENCES Job(id) NULL
);

INSERT INTO Employee VALUES ( employee_sequence.nextval, 'Faris', 'Owner', 'Dzebrail', TO_DATE('8/24/1998','M/d/yyyy'),'2408998170040','060/338-1032','sisicfaris@hotmail.com','77769777123',10000,NULL,3,5,3 );
INSERT INTO Employee VALUES ( employee_sequence.nextval, 'Mirza', 'Sinanovic', 'Narcis', TO_DATE('9/9/1998','M/d/yyyy'),'0909998170026','061/580-789','micki-91@hotmail.com','1231230991',5000,NULL,5,1,2 );
INSERT INTO Employee VALUES ( employee_sequence.nextval, 'Ajla', 'Salispahic', 'Rusmir', TO_DATE('1/1/1999','M/d/yyyy'),'0101999175016','062/572-168','aj-la-s@hotmail.com','199901234567',5000,NULL,5,1,2 );
INSERT INTO Employee VALUES ( employee_sequence.nextval, 'Julian', 'Cutthroat', 'Jack', TO_DATE('7/7/1997','M/d/yyyy'),'0707997170942','060/123-4567','juli69@outlook.com','987650002',3500,NULL,4,3,4 );
INSERT INTO Employee VALUES ( employee_sequence.nextval, 'Jane', 'Doe', 'John', TO_DATE('1/1/1970','M/d/yyyy'),'0101970123432','063/333-333','janedoe@outlook.com','00042011169',4000,NULL,1,5,1 );

ALTER TABLE Employee
ADD manager  INTEGER REFERENCES Employee(id) NULL;

UPDATE  Employee SET manager = 1 WHERE id = 2;
UPDATE  Employee SET manager = 1 WHERE id = 3;
UPDATE  Employee SET manager = 1 WHERE id = 4;
UPDATE  Employee SET manager = 1 WHERE id = 5;

ALTER TABLE Department
ADD manager  INTEGER REFERENCES Employee(id) NULL;

UPDATE Department SET manager = 1 WHERE id = 3;
UPDATE Department SET manager = 1 WHERE id = 5;
UPDATE Department SET manager = 2 WHERE id = 1;
UPDATE Department SET manager = 2 WHERE id = 4;
UPDATE Department SET manager = 5 WHERE id = 2;

CREATE SEQUENCE contract_sequence
  START WITH 1
  INCREMENT BY 1
  NOCACHE
  NOCYCLE;

CREATE TABLE Contract (
  id INTEGER PRIMARY KEY ,
  contract_number VARCHAR(255) NOT NULL UNIQUE,
  start_date DATE NOT NULL ,
  end_date DATE NOT NULL ,
  job INTEGER REFERENCES Job(id) NULL,
  employee INTEGER REFERENCES Employee(id) NULL
);

INSERT INTO Contract VALUES ( contract_sequence.nextval, 'A123-11195',TO_DATE('M/d/yyyy','1/1/2019'),TO_DATE('M/d/yyyy','1/1/2029'),1,5 );
INSERT INTO Contract VALUES ( contract_sequence.nextval, 'A123-11196',TO_DATE('M/d/yyyy','2/1/2019'),TO_DATE('M/d/yyyy','2/1/2024'),4,4 );
INSERT INTO Contract VALUES ( contract_sequence.nextval, 'C123-12311',TO_DATE('M/d/yyyy','2/1/2019'),TO_DATE('M/d/yyyy','2/1/2024'),1,2 );
INSERT INTO Contract VALUES ( contract_sequence.nextval, 'B127-34112',TO_DATE('M/d/yyyy','2/1/2019'),TO_DATE('M/d/yyyy','2/1/2024'),1,2 );



