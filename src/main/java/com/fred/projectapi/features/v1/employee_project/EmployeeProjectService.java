package com.fred.projectapi.features.v1.employee_project;

import com.fred.projectapi.model.entity.EmployeeProject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeProjectService {

    private final EmployeeProjectRepository employeeProjectRepository;

    public void saveAll(final List<EmployeeProject> employeeProjects) {
        employeeProjectRepository.saveAll(employeeProjects);
    }

    public List<EmployeeProject> findAllByProjectId(final Long projectId) {
        return employeeProjectRepository.findAllByProjectId(projectId);
    }

    public void deleteAllByProjectId(final Long projectId) {
        employeeProjectRepository.deleteAllByProjectId(projectId);
    }
}
