package com.devspotlight.devspotlight.service;

import com.devspotlight.devspotlight.dto.RepositoryDTO;

import java.util.List;
import java.util.Optional;

public interface RepositoryService {

    Optional<RepositoryDTO> createRepository(RepositoryDTO request);

    List<RepositoryDTO> getAllRepositoriesByUser(Long userId);
}
