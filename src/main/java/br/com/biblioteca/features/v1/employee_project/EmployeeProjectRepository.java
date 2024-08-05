package com.fred.projectapi.features.v1.employee_project;

import com.fred.projectapi.model.entity.EmployeeProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {

    @Query("from EmployeeProject where project.id = :projectId")
    List<EmployeeProject> findAllByProjectId(@Param("projectId") Long projectId);

    @Modifying
    @Query("DELETE FROM EmployeeProject ep WHERE ep.project.id = :projectId")
    void deleteAllByProjectId(@Param("projectId") Long projectId);
}
