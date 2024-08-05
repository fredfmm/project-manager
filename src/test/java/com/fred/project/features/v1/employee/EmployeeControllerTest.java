package com.fred.project.features.v1.employee;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fred.project.model.entity.Employee;
import com.fred.project.model.enums.Assignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;


    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEmployeeSuccessTest() throws Exception {
        Employee employee = Employee.builder()
                .name("John Doe")
                .assignment(Assignment.FUNCIONARIO)
                .build();

        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

        String employeeJson = objectMapper.writeValueAsString(employee);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.assignment").value("FUNCIONARIO"));

        verify(employeeService).createEmployee(any(Employee.class));
    }

    @Test
    void getAllManagersSuccessTest() throws Exception {
        Employee manager1 = Employee.builder()
                .name("Manager One")
                .assignment(Assignment.GERENTE)
                .build();

        Employee manager2 = Employee.builder()
                .name("Manager Two")
                .assignment(Assignment.GERENTE)
                .build();

        List<Employee> managers = List.of(manager1, manager2);

        when(employeeService.findAllEmployeesByAssignment(Assignment.GERENTE)).thenReturn(managers);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee")
                .accept(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Manager One"))
                .andExpect(jsonPath("$[1].name").value("Manager Two"));

        verify(employeeService).findAllEmployeesByAssignment(Assignment.GERENTE);
    }
}