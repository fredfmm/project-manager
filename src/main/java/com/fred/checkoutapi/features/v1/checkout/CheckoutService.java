package com.fred.checkoutapi.features.v1.checkout;

import com.fred.checkoutapi.model.entity.Employee;
import com.fred.checkoutapi.model.enums.Assignment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> findAllEmployeesByAssignment(final Assignment assignment) {
        return employeeRepository.findAllEmployeesByAssignment(assignment);
    }

    @Transactional
    public Employee createEmployee(final Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> findById(final Long employeeId) {
        return employeeRepository.findById(employeeId);
    }
}
