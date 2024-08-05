package com.fred.projectapi.features.v1.project;

import com.fred.projectapi.model.entity.Project;
import com.fred.projectapi.model.request.ProjectRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/project")
@AllArgsConstructor
public class ProjectController {

	private final ProjectService projectService;
	private static final String PROJECT = "project";

	@GetMapping
	public RedirectView redirectToProjectList() {
		return new RedirectView("/project/list");
	}

	@GetMapping("/list")
	public String listProjects(Model model) {
		List<Project> projects = projectService.getAllProjects();
		model.addAttribute("projects", projects);
		return "list-project";
	}

	@GetMapping("/add")
	public String showAddProjectForm() {
		return "add-project";
	}

	@PostMapping("/add")
	public String addProject(@RequestBody ProjectRequest projectRequest, RedirectAttributes redirectAttributes) {
		try {
			projectService.createProject(projectRequest);
			redirectAttributes.addFlashAttribute("success", "Project added successfully.");
			return "redirect:/project/list";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Failed to add project.");
			return "redirect:/project/add";
		}
	}

	@DeleteMapping("/delete/{projectId}")
	public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
		try {
			projectService.deleteProject(projectId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/edit/{projectId}")
	public String showEditForm(@PathVariable Long projectId, Model model) {
		Project project = projectService.getProjectById(projectId);
		model.addAttribute(PROJECT, project);
		return "edit-project";
	}

	@PutMapping("/update/{projectId}")
	public ResponseEntity<String> updateProject(@PathVariable Long projectId, @RequestBody ProjectRequest projectRequest) {
		try {
			projectService.updateProject(projectId, projectRequest);
			return ResponseEntity.ok().body("Project updated successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while processing the request.");
		}
	}

	@GetMapping("/associate/{projectId}")
	public String showAssociateEmployeesForm(@PathVariable Long projectId, Model model) {
		Map<String, Object> projectAndEmployees = projectService.getProjectAndEmployees(projectId);
		model.addAttribute(PROJECT, projectAndEmployees.get(PROJECT));
		model.addAttribute("employees", projectAndEmployees.get("employees"));
		return "associate-employees";
	}

	@PostMapping("/associate/{projectId}")
	public String associateEmployees(@PathVariable Long projectId, @RequestParam List<Long> employees, RedirectAttributes redirectAttributes) {
		try {
			projectService.associateEmployees(projectId, employees);
			redirectAttributes.addFlashAttribute("success", "Employees associated successfully.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Failed to associate employees.");
		}
		return "redirect:/project/list";
	}

	@PutMapping("/associate/{projectId}")
	public ResponseEntity<String> associateEmployeesViaPut(@PathVariable Long projectId, @RequestBody List<Long> employeeIds) {
		try {
			projectService.associateEmployees(projectId, employeeIds);
			return ResponseEntity.ok().body("Employees associated successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An error occurred while processing the request.");
		}
	}

}

