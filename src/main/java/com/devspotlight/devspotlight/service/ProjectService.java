package com.devspotlight.devspotlight.service;

import com.devspotlight.devspotlight.dto.ProjectDTO;
import com.devspotlight.devspotlight.exceptions.ProjectNotFoundException;
import com.devspotlight.devspotlight.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Optional<ProjectDTO> createProject(ProjectDTO request);

    List<ProjectDTO> getAllProjects();

    List<ProjectDTO> getAllRepositoriesByUser(Long userId);

    Optional<ProjectDTO> getProjectByUserIAndProjectName(Long userId, String projectName) throws UserNotFoundException, ProjectNotFoundException;

    Optional<ProjectDTO> getProjectByUserAndId(Long userId, Long id);

    void deleteProjectById(Long id);
}
