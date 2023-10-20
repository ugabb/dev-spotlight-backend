package com.devspotlight.devspotlight.repository;

import com.devspotlight.devspotlight.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameOrEmail(String username, String email);
}
