package com.fred.projectapi.features.v1.project;

import com.fred.projectapi.model.request.ProjectRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.fred.projectapi.model.entity.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path = "/project")
@AllArgsConstructor
public class ProjectController {

}

