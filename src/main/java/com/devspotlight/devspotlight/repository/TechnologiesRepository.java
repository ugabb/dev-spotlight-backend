package com.devspotlight.devspotlight.repository;

import com.devspotlight.devspotlight.model.Technologies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologiesRepository extends JpaRepository<Technologies, Long> {
}
