package com.fred.projectapi.features.v1.employee;

import com.fred.projectapi.model.entity.Employee;
import com.fred.projectapi.model.enums.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
