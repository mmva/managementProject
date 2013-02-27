CREATE DATABASE management_projects;

USE management_projects;

CREATE TABLE employees (
	id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
	firt_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100) NOT NULL,
	last_name2 VARCHAR(100),
	nif CHAR(10) NOT NULL,
	email VARCHAR(100) NOT NULL,
	`password` VARCHAR(20) NOT NULL,
	`profile` ENUM('PROGRAMADOR', 'ANALISTA', 'JEFE_PROYECTO', 'ADMINISTRADOR' ), 
	active ENUM('ACTIVO','INACTIVO'),
	PRIMARY KEY (id),
	UNIQUE KEY email_uk(email)
);



CREATE TABLE projects (
	id INT UNSIGNED AUTO_INCREMENT NOT NULL,
	`name` VARCHAR(100) NOT NULL,
	description VARCHAR(255),
	PRIMARY KEY (id)
);

CREATE TABLE tasks (
	id BIGINT UNSIGNED AUTO_INCREMENT NOT NULL,
	id_project INT UNSIGNED NOT NULL,
	id_employee BIGINT UNSIGNED, 
	`name` VARCHAR(100) NOT NULL,
	description VARCHAR(255) NOT NULL,
	estimate_at TIME NOT NULL,
	date_start DATETIME,
	date_finish DATETIME,
	date_last_update TIMESTAMP,
	PRIMARY KEY (id),
	FOREIGN KEY ( id_project ) REFERENCES projects(id), 
	FOREIGN KEY ( id_employee ) REFERENCES employees(id) 
);



INSERT INTO projects 
SET `name` = 'Gestion proyectos',
	description = 'Creacion de una pagina web';

INSERT INTO employees 
SET firt_name = 'Alberto',
	last_name = 'Guerrero',
	last_name2 = 'Galan',
	nif = '12345678-Y',
	email = 'alberto@empresa.es',
	`password` = "asvd",
	`profile` = 'PROGRAMADOR',
	active = 'ACTIVO';

INSERT INTO employees 
SET firt_name = 'Ruben',
	last_name = 'Montoro',
	last_name2 = 'Sevilla',
	nif = '87654321-A',
	email = 'ruben@empresa.es',
	`password` = "sadas",
	`profile` = 'ANALISTA',
	active = 'ACTIVO';

INSERT INTO employees 
SET firt_name = 'David',
	last_name = 'Garcia',
	last_name2 = 'Rodriguez',
	nif = '12343214-W',
	email = 'david@empresa.es',
	`password` = "aaa",
	`profile` = 'JEFE_PROYECTO',
	active = 'ACTIVO';

INSERT INTO tasks 
SET id_project = 1,
	id_employee = 1, 
	`name` = 'Creacion base de datos',
	description = 'descripcion',
	estimate_at = '08:00:00';

INSERT INTO tasks 
SET id_project = 1,
	id_employee = 2, 
	`name` = 'Creacion de la estructura proyecto',
	description = 'Crear estructura para trabajar',
	estimate_at = '01:00:00';

INSERT INTO tasks 
SET id_project = 1,
	id_employee = 3, 
	`name` = 'Documentacion',
	description = 'Creacion de los documentos',
	estimate_at = '03:00:00';

/*SELECT * FROM employees;
SELECT * FROM projects;
SELECT * FROM tasks;*/