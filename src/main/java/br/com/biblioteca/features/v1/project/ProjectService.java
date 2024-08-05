package com.fred.projectapi.features.v1.project;

import com.fred.projectapi.exceptions.NotFoundException;
import com.fred.projectapi.features.v1.employee.EmployeeService;
import com.fred.projectapi.features.v1.employee_project.EmployeeProjectService;
import com.fred.projectapi.model.entity.Employee;
import com.fred.projectapi.model.entity.EmployeeProject;
import com.fred.projectapi.model.entity.Project;
import com.fred.projectapi.model.enums.Assignment;
import com.fred.projectapi.model.enums.Status;
import com.fred.projectapi.model.request.ProjectRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepository projectRepository;
	private final EmployeeService employeeService;
	private final EmployeeProjectService employeeProjectService;

	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	@Transactional
	public void createProject(final ProjectRequest projectRequest) {
		Employee manager = validateAndGetManager(projectRequest.getManagerId());
		Project project = buildProjectFromRequest(projectRequest, manager);
		projectRepository.save(project);
	}

	@Transactional
	public void updateProject(final Long projectId, final ProjectRequest projectDetails) {
		Project project = getProjectById(projectId);
		Employee manager = validateAndGetManager(projectDetails.getManagerId());
		updateProjectDetails(project, projectDetails, manager);
		projectRepository.save(project);
	}

	@Transactional
	public void deleteProject(final Long projectId) {
		Project project = getProjectById(projectId);
		validateProjectForDeletion(project);
		employeeProjectService.deleteAllByProjectId(project.getId());
		projectRepository.delete(project);
	}

	@Transactional
	public void associateEmployees(final Long projectId, final List<Long> employeeIds) {
		Project project = getProjectById(projectId);
		List<EmployeeProject> employeeProjects = buildEmployeeProjectList(project, employeeIds);
		employeeProjectService.deleteAllByProjectId(projectId);
		employeeProjectService.saveAll(employeeProjects);
	}

	private Employee validateAndGetManager(final Long managerId) {
		Employee manager = employeeService.findById(managerId)
				.orElseThrow(() -> new NotFoundException("Employee not found with id: " + managerId));
		if (!"GERENTE".equals(manager.getAssignment().name())) {
			throw new IllegalArgumentException("Employee with id " + manager.getId() + " is not a manager.");
		}
		return manager;
	}

	public Project getProjectById(final Long projectId) {
		return projectRepository.findById(projectId)
				.orElseThrow(() -> new NotFoundException("Project not found with id: " + projectId));
	}

	private void validateProjectForDeletion(final Project project) {
		if (project.getStatus() == Status.INICIADO || project.getStatus() == Status.EM_ANDAMENTO || project.getStatus() == Status.ENCERRADO) {
			throw new IllegalArgumentException("Projects in status INICIADO, EM ANDAMENTO or ENCERRADO cannot be deleted");
		}
	}

	private Project buildProjectFromRequest(final ProjectRequest projectRequest, final Employee manager) {
		return Project.builder()
				.name(projectRequest.getName())
				.startDate(projectRequest.getStartDate())
				.estimatedEndDate(projectRequest.getEstimatedEndDate())
				.endDate(projectRequest.getEndDate())
				.description(projectRequest.getDescription())
				.status(projectRequest.getStatus())
				.budget(projectRequest.getBudget())
				.risk(projectRequest.getRisk())
				.manager(manager)
				.build();
	}

	private void updateProjectDetails(final Project project, final ProjectRequest projectDetails, final Employee manager) {
		project.setName(projectDetails.getName());
		project.setStartDate(projectDetails.getStartDate());
		project.setManager(manager);
		project.setEstimatedEndDate(projectDetails.getEstimatedEndDate());
		project.setEndDate(projectDetails.getEndDate());
		project.setBudget(projectDetails.getBudget());
		project.setDescription(projectDetails.getDescription());
		project.setStatus(projectDetails.getStatus());
		project.setRisk(projectDetails.getRisk());
	}

	private List<EmployeeProject> buildEmployeeProjectList(final Project project, final List<Long> employeeIds) {
		List<EmployeeProject> employeeProjects = new ArrayList<>();
		for (Long employeeId : employeeIds) {
			Employee employee = employeeService.findById(employeeId)
					.orElseThrow(() -> new IllegalArgumentException("Employee not found"));
			if (!"FUNCIONARIO".equals(employee.getAssignment().name())) {
				throw new IllegalArgumentException("Employee with id " + employee.getId() + " is not a functionary.");
			}
			EmployeeProject employeeProject = new EmployeeProject();
			employeeProject.setProject(project);
			employeeProject.setEmployee(employee);
			employeeProjects.add(employeeProject);
		}
		return employeeProjects;
	}

	public Map<String, Object> getProjectAndEmployees(final Long projectId) {
		Project project = getProjectById(projectId);
		List<Employee> employees = employeeService.findAllEmployeesByAssignment(Assignment.FUNCIONARIO);
		List<EmployeeProject> employeesProject = employeeProjectService.findAllByProjectId(projectId);

		employees.forEach(employee -> employee.setSelected(employeesProject.stream()
				.anyMatch(ep -> ep.getEmployee().getId().equals(employee.getId()))));

		Map<String, Object> result = new HashMap<>();
		result.put("project", project);
		result.put("employees", employees);
		return result;
	}

}
