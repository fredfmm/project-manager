package com.fred.projectapi.features.v1.employee;

import com.fred.projectapi.model.entity.Employee;
import com.fred.projectapi.model.enums.Assignment;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/employee")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @GetMapping
    public List<Employee> getAllManagers() {
        return employeeService.findAllEmployeesByAssignment(Assignment.GERENTE);
    }
}