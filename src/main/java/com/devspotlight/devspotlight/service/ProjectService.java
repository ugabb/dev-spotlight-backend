package com.devspotlight.devspotlight.service;

import com.devspotlight.devspotlight.dto.ProjectDTO;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    Optional<ProjectDTO> createRepository(ProjectDTO request);

    List<ProjectDTO> getAllProjects();

    List<ProjectDTO> getAllRepositoriesByUser(Long userId);

    Optional<ProjectDTO> getProjectByUserAndId(Long userId, Long id);
}
