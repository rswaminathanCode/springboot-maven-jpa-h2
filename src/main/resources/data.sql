DROP TABLE IF EXISTS Employee;

CREATE TABLE Employee (
  ID VARCHAR(250)  PRIMARY KEY NOT NULL,
  LOGIN VARCHAR(250) NOT NULL,
  NAME VARCHAR(250) NOT NULL,
  SALARY double not null,
  STARTDATE DATE DEFAULT not NULL
);

ALTER TABLE Employee ADD CONSTRAINT ID_LOGIN_UNIQUE UNIQUE(ID, LOGIN);

--insert into Employee (ID, LOGIN, NAME, SALARY, STARTDATE)values ( 'E0001', 'hpotter', 'Harry Potter', 1234.00, to_date('16-11-2001','DD-MM-YYYY'));
--insert into Employee (ID, LOGIN, NAME, SALARY, STARTDATE)values ( 'E0002','rwesley','Ron Weasley',19234.50,to_date('2001-11-16','YYYY-MM-DD'));
--insert into Employee (ID, LOGIN, NAME, SALARY, STARTDATE)values ( 'E0003','ssnape','Severus Snape',4000.0,to_date('2001-11-16','YYYY-MM-DD'));
--insert into Employee (ID, LOGIN, NAME, SALARY, STARTDATE)values ( 'E0004','rhagrid','Rubeus Hagrid',3999.999,to_date('16-Nov-01','DD-MON-YY'));

