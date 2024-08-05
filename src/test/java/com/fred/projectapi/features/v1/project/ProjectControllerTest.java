package com.fred.projectapi.features.v1.project;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fred.projectapi.features.v1.employee.EmployeeService;
import com.fred.projectapi.model.entity.Employee;
import com.fred.projectapi.model.entity.Project;
import com.fred.projectapi.model.enums.Assignment;
import com.fred.projectapi.model.enums.Risk;
import com.fred.projectapi.model.enums.Status;
import com.fred.projectapi.model.request.ProjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void redirectToProjectListTest() throws Exception {
        mockMvc.perform(get("/project"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/list"));
    }

    @Test
    void showAddProjectFormTest() throws Exception {
        mockMvc.perform(get("/project/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-project"));
    }

    @Test
    void addProjectSuccessTest() throws Exception {
        Employee manager = Employee.builder()
                .id(1L)
                .assignment(Assignment.GERENTE)
                .name("teste-manager")
                .build();

        when(employeeService.createEmployee(any(Employee.class))).thenReturn(manager);

        ProjectRequest projectRequest = ProjectRequest.builder()
                .name("Project 1")
                .startDate(new Date())
                .estimatedEndDate(new Date())
                .endDate(new Date())
                .description("Description 1")
                .status(Status.PLANEJADO)
                .budget(1000.0)
                .risk(Risk.BAIXO)
                .managerId(manager.getId())
                .build();

        String projectRequestJson = new ObjectMapper().writeValueAsString(projectRequest);

        mockMvc.perform(post("/project/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectRequestJson))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/list"))
                .andExpect(flash().attribute("success", "Project added successfully."));

        verify(projectService, times(1)).createProject(any(ProjectRequest.class));
    }

    @Test
    void listProjectsTest() throws Exception {
        Employee manager = Employee.builder()
                .assignment(Assignment.GERENTE)
                .name("teste-manager")
                .build();
        manager = employeeService.createEmployee(manager);
        List<Project> projects = Arrays.asList(
                Project.builder()
                        .id(1L)
                        .name("Project 1")
                        .startDate(new Date())
                        .estimatedEndDate(new Date())
                        .endDate(new Date())
                        .description("Description 1")
                        .status(Status.PLANEJADO)
                        .budget(1000.0)
                        .risk(Risk.BAIXO)
                        .manager(manager)
                        .build(),
                Project.builder()
                        .id(2L)
                        .name("Project 2")
                        .startDate(new Date())
                        .estimatedEndDate(new Date())
                        .endDate(new Date())
                        .description("Description 2")
                        .status(Status.INICIADO)
                        .budget(2000.0)
                        .risk(Risk.MEDIO)
                        .manager(manager)
                        .build()
        );

        when(projectService.getAllProjects()).thenReturn(projects);

        mockMvc.perform(get("/project/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("list-project"))
                .andExpect(model().attribute("projects", projects));
    }

    @Test
    void deleteProjectSuccessTest() throws Exception {
        Long projectId = 1L;

        mockMvc.perform(delete("/project/delete/{id}", projectId))
                .andExpect(status().isOk());

        verify(projectService, times(1)).deleteProject(projectId);
    }

    @Test
    void showEditFormSuccessTest() throws Exception {
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);
        project.setName("Project 1");

        when(projectService.getProjectById(projectId)).thenReturn(project);

        mockMvc.perform(get("/project/edit/{id}", projectId))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-project"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attribute("project", project));
    }

    @Test
    void updateProjectSuccessTest() throws Exception {
        Long projectId = 1L;
        Employee manager = Employee.builder()
                .id(1L)
                .assignment(Assignment.GERENTE)
                .name("teste-manager")
                .build();

        when(employeeService.createEmployee(any(Employee.class))).thenReturn(manager);

        ProjectRequest projectRequest = ProjectRequest.builder()
                .name("Project 1")
                .startDate(new Date())
                .estimatedEndDate(new Date())
                .endDate(new Date())
                .description("Description 1")
                .status(Status.PLANEJADO)
                .budget(1000.0)
                .risk(Risk.BAIXO)
                .managerId(manager.getId())
                .build();


        String projectRequestJson = objectMapper.writeValueAsString(projectRequest);

        doNothing().when(projectService).updateProject(anyLong(), any(ProjectRequest.class));

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/project/update/{projectId}", projectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(projectRequestJson));

        resultActions.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Project updated successfully."));

        verify(projectService).updateProject(projectId, projectRequest);
    }

    @Test
    void showAssociateEmployeesFormTest() throws Exception {
        Long projectId = 1L;
        Project project = new Project();
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        List<Employee> employees = Arrays.asList(employee1, employee2);

        Map<String, Object> projectAndEmployees = new HashMap<>();
        projectAndEmployees.put("project", project);
        projectAndEmployees.put("employees", employees);

        when(projectService.getProjectAndEmployees(projectId)).thenReturn(projectAndEmployees);

        mockMvc.perform(get("/project/associate/{projectId}", projectId))
                .andExpect(status().isOk())
                .andExpect(view().name("associate-employees"))
                .andExpect(model().attributeExists("project"))
                .andExpect(model().attributeExists("employees"))
                .andExpect(model().attribute("project", project))
                .andExpect(model().attribute("employees", employees));

        verify(projectService).getProjectAndEmployees(projectId);
    }

    @Test
    void associateEmployeesSuccessTest() throws Exception {
        Long projectId = 1L;
        List<Long> employeeIds = Arrays.asList(2L, 3L);

        doNothing().when(projectService).associateEmployees(anyLong(), anyList());

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/project/associate/{projectId}", projectId)
                .param("employees", employeeIds.stream().map(String::valueOf).toArray(String[]::new))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED));

        resultActions.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/project/list"))
                .andExpect(flash().attribute("success", "Employees associated successfully."));

        verify(projectService).associateEmployees(projectId, employeeIds);
    }

    @Test
    void associateEmployeesViaPutSuccessTest() throws Exception {
        Long projectId = 1L;
        List<Long> employeeIds = Arrays.asList(2L, 3L);

        doNothing().when(projectService).associateEmployees(anyLong(), anyList());

        String employeeIdsJson = objectMapper.writeValueAsString(employeeIds);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/project/associate/{projectId}", projectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeIdsJson));

        resultActions.andExpect(status().isOk())
                .andExpect(content().string("Employees associated successfully."));

        verify(projectService).associateEmployees(projectId, employeeIds);
    }
}