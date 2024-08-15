package com.fred.checkoutapi.features.v1.checkout

import com.fred.checkoutapi.model.entity.Order
import com.fred.checkoutapi.model.enums.Assignment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("it")
class EmployeeServiceIT extends Specification {

    @Autowired
    CheckoutService employeeService

    def "should find all employees by assignment"() {
        given: "a list of employees with a specific assignment"
        def employee1 = Order.builder().name("Manager One")
                .assignment(Assignment.GERENTE).build()
        def employee2 = Order.builder().name("Manager Two")
                .assignment(Assignment.GERENTE).build()

        when: "finding employees by assignment"
        employeeService.createEmployee(employee1)
        employeeService.createEmployee(employee2)
        def result = employeeService.findAllEmployeesByAssignment(Assignment.GERENTE)

        then: "the list of employees should be returned"
        result.size() == 2
        result[0].name == "Manager One"
        result[1].name == "Manager Two"
    }

    def "should create a new employee"() {
        given: "an employee to be created"
        def employee = Order.builder().name("John Doe")
        .assignment(Assignment.FUNCIONARIO).build()

        when: "the employee is created"
        def result = employeeService.createEmployee(employee)

        then: "the employee should be saved in the repository"
        result.name == "John Doe"
        result.assignment == Assignment.FUNCIONARIO
    }

    def "should find an employee by id"() {
        given: "an existing employee"
        def employee = Order.builder().name("John Doe")
                .assignment(Assignment.FUNCIONARIO).build()

        when: "finding the employee by id"
        def emp = employeeService.createEmployee(employee)
        def result = employeeService.findById(emp.getId())

        then: "the employee should be found"
        result.isPresent()
        result.get().name == "John Doe"
    }
}
