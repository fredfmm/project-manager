package com.fred.project.features.v1.employee;

import com.fred.project.model.entity.Employee;
import com.fred.project.model.enums.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("from Employee where assignment = :assignment")
    List<Employee> findAllEmployeesByAssignment(@Param("assignment") Assignment assignment);

}
