CREATE TABLE Persons (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR(50),
  age  INTEGER
);

CREATE TABLE Companies (
  id         INTEGER IDENTITY PRIMARY KEY,
  name VARCHAR(50)
);

CREATE TABLE Employees (
  id         INTEGER IDENTITY PRIMARY KEY,
  person_id INTEGER REFERENCES Persons (id),
  company_id INTEGER REFERENCES Companies (id)
);