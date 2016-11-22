CREATE TABLE Persons (
  id         INTEGER AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR(50),
  age  INTEGER
);

CREATE TABLE Companies (
  id         INTEGER AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50)
);

CREATE TABLE Employees (
  id         INTEGER AUTO_INCREMENT PRIMARY KEY,
  personId INTEGER REFERENCES Persons (id),
  companyId INTEGER REFERENCES Companies (id)
);