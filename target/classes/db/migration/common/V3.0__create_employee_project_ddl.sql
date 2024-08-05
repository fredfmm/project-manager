CREATE TABLE IF NOT EXISTS employee_project
(
    id bigserial NOT NULL,
    project_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    CONSTRAINT pk_employee_project PRIMARY KEY (id),
    CONSTRAINT fk_project FOREIGN KEY (project_id)
    REFERENCES project (id) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fk_employee FOREIGN KEY (employee_id)
    REFERENCES employee (id) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE NO ACTION
);
