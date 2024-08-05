CREATE TABLE IF NOT EXISTS employee
(
    id bigserial NOT NULL,
    name VARCHAR(100) NOT NULL,
    assignment VARCHAR(100) NOT NULL,
    CONSTRAINT pk_employee PRIMARY KEY (id)
);