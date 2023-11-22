package com.devspotlight.devspotlight.repository;

import com.devspotlight.devspotlight.model.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositoryRepository extends JpaRepository<Repository, Long> {
    List<Repository> findByUserId(Long userId);
}
