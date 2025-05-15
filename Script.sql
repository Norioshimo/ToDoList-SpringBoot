
--CREAR BASE DE DATOS
CREATE DATABASE db_todolist;
CREATE ROLE todolist WITH LOGIN PASSWORD '123';

GRANT ALL PRIVILEGES ON DATABASE db_todolist TO todolist;
ALTER ROLE todolist WITH SUPERUSER;


CREATE TABLE users(
	users_id serial PRIMARY KEY,
	nombre character varying(100) NOT NULL,
	email character varying(100) NOT NULL,
	password CHARACTER VARYING(255)  NOT NULL,
	fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE users OWNER TO todolist;


CREATE TABLE tasks(
	tasks_id serial PRIMARY KEY,
	titulo CHARACTER VARYING(50) ,
	descripcion CHARACTER VARYING(1000),
	fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	users_id INT,
    CONSTRAINT fk_usuario
        FOREIGN KEY (users_id)
        REFERENCES users(users_id)
        ON DELETE RESTRICT
);
ALTER TABLE tasks OWNER TO todolist;