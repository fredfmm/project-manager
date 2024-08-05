package com.fred.project.features.v1.project

import com.fred.project.features.v1.employee.EmployeeService
import com.fred.project.features.v1.employee_project.EmployeeProjectService
import com.fred.project.model.entity.Employee
import com.fred.project.model.entity.EmployeeProject
import com.fred.project.model.entity.Project
import com.fred.project.model.enums.Assignment
import com.fred.project.model.enums.Risk
import com.fred.project.model.enums.Status
import com.fred.project.model.request.ProjectRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("it")
class ProjectServiceIT extends Specification {

    @Autowired
    private ProjectService projectService

    @Autowired
    private EmployeeService employeeService

    @Autowired
    private EmployeeProjectService employeeProjectService

    private static ProjectRequest createProjectRequest(String name, Long managerId, Date startDate, Date estimatedEndDate, Date endDate, String description, Status status, Double budget, Risk risk) {
        return ProjectRequest.builder()
                .name(name)
                .managerId(managerId)
                .startDate(startDate)
                .estimatedEndDate(estimatedEndDate)
                .endDate(endDate)
                .description(description)
                .status(status)
                .budget(budget)
                .risk(risk)
                .build()
    }

    def "should create a new project"() {
        given: "an existing manager"
        Employee manager = Employee.builder()
                .assignment(Assignment.GERENTE)
                .name("teste-manager")
                .build()
        manager = employeeService.createEmployee(manager)

        and: "a project request"
        ProjectRequest projectRequest = createProjectRequest(
                "New Project",
                manager.getId(),
                new Date(),
                new Date(),
                new Date(),
                "Project Description",
                Status.CANCELADO,
                1000.0,
                Risk.BAIXO
        )

        when: "the project is created"
        projectService.createProject(projectRequest)

        then: "the project should be found in the repository"
        def projects = projectService.getAllProjects()
        projects.size() == 1
        projects[0].getName() == "New Project"
    }

    def "should update an existing project"() {
        given: "an existing manager and project"
        Employee manager = Employee.builder()
        .assignment(Assignment.GERENTE)
        .name("teste-manager")
        .build()
        manager = employeeService.createEmployee(manager)

        ProjectRequest projectRequest = createProjectRequest(
                "Existing Project",
                manager.getId(),
                new Date(),
                new Date(),
                new Date(),
                "Existing Project Description",
                Status.CANCELADO,
                1000.0,
                Risk.BAIXO
        )

        projectService.createProject(projectRequest)
        def project = projectService.getAllProjects().get(0)

        and: "updated project details"
        ProjectRequest updatedProjectRequest = createProjectRequest(
                "Updated Project",
                manager.getId(),
                new Date(),
                new Date(),
                new Date(),
                "Updated Project Description",
                Status.CANCELADO,
                2000.0,
                Risk.MEDIO
        )

        when: "the project is updated"
        projectService.updateProject(project.getId(), updatedProjectRequest)

        then: "the project should be updated in the repository"
        def updatedProject = projectService.getProjectById(project.getId())
        updatedProject.getName() == "Updated Project"
        updatedProject.getDescription() == "Updated Project Description"
        updatedProject.getStatus() == Status.CANCELADO
        updatedProject.getBudget() == 2000.0
        updatedProject.getRisk() == Risk.MEDIO
    }

    def "should delete an existing project"() {
        given: "an existing manager and project"
        Employee manager = Employee.builder()
                .assignment(Assignment.GERENTE)
                .name("teste-manager")
                .build()
        manager = employeeService.createEmployee(manager)

        ProjectRequest projectRequest = createProjectRequest(
                "Project to Delete",
                manager.getId(),
                new Date(),
                new Date(),
                new Date(),
                "Project Description",
                Status.CANCELADO,
                1000.0,
                Risk.BAIXO
        )

        projectService.createProject(projectRequest)
        def project = projectService.getAllProjects().get(0)

        when: "the project is deleted"
        def projects = projectService.getAllProjects()
        projectService.deleteProject(project.getId())

        then: "the project should be removed from the repository"
        def projects1 = projectService.getAllProjects()
        projects.size() != projects1.size()
    }

    def "should associate employees with a project"() {
        given: "an existing manager, project, and employees"
        Employee manager = Employee.builder()
                .assignment(Assignment.GERENTE)
                .name("teste-manager")
        .build()
        manager = employeeService.createEmployee(manager)

        Employee employee1 = Employee.builder()
                .assignment(Assignment.FUNCIONARIO)
                .name("teste-func1")
               .build()
        employee1 = employeeService.createEmployee(employee1)

        Employee employee2 = Employee.builder()
                .assignment(Assignment.FUNCIONARIO)
                .name("teste-func1")
                .build()
        employee2 = employeeService.createEmployee(employee2)

        ProjectRequest projectRequest = createProjectRequest(
                "Project to Associate Employees",
                manager.getId(),
                new Date(),
                new Date(),
                new Date(),
                "Project Description",
                Status.CANCELADO,
                1000.0,
                Risk.BAIXO
        )

        projectService.createProject(projectRequest)
        def project = projectService.getAllProjects().get(0)

        when: "employees are associated with the project"
        projectService.associateEmployees(project.getId(), Arrays.asList(employee1.getId(), employee2.getId()))

        then: "the employees should be associated with the project"
        def employeesProject = employeeProjectService.findAllByProjectId(project.getId())
        employeesProject.size() == 2
    }

    def "should get project and associated employees"() {
        given: "a project ID and a project with employees"
        Long projectId = 1L
        Project project = new Project(id: projectId, name: "Project Name", startDate: new Date(), estimatedEndDate: new Date(), endDate: new Date(), description: "Project Description", status: Status.CANCELADO, budget: 2000.0, risk: Risk.BAIXO)
        Employee employee1 = new Employee(id: 1L, name: "Employee 1", assignment: Assignment.FUNCIONARIO)
        Employee employee2 = new Employee(id: 2L, name: "Employee 2", assignment: Assignment.FUNCIONARIO)
        List<Employee> employees = [employee1, employee2]
        List<EmployeeProject> employeesProject = [new EmployeeProject(employee: employee1, project: project), new EmployeeProject(employee: employee2, project: project)]

        projectService.getProjectById(projectId) >> project
        employeeService.findAllEmployeesByAssignment(Assignment.FUNCIONARIO) >> employees
        employeeProjectService.findAllByProjectId(projectId) >> employeesProject

        when: "fetching the project and employees"
        Map<String, Object> result = projectService.getProjectAndEmployees(projectId)

        then: "the result should contain the project and employees"
        result != null
    }

}
