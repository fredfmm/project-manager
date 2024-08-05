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



}

