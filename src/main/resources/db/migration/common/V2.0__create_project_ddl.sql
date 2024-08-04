CREATE TABLE project (
         id bigserial NOT NULL,
         name VARCHAR(200) NOT NULL,
         start_date DATE,
         estimated_end_date DATE,
         end_date DATE,
         description VARCHAR(5000),
         status VARCHAR(45),
         budget FLOAT,
         risk VARCHAR(45),
         manager_id BIGINT NOT NULL,
         CONSTRAINT pk_project PRIMARY KEY (id),
         CONSTRAINT fk_manager FOREIGN KEY (manager_id)
             REFERENCES employee (id) MATCH SIMPLE
             ON UPDATE NO ACTION ON DELETE NO ACTION
);
