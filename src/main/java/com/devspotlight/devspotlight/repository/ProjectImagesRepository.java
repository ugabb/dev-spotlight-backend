package com.devspotlight.devspotlight.repository;

import com.devspotlight.devspotlight.model.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectImagesRepository extends JpaRepository<ProjectImage, Long> {
}
