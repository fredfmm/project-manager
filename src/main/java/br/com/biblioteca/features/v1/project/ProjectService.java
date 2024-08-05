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

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectService {

	
}
