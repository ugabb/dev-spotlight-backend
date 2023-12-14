package com.devspotlight.devspotlight.service;

import com.devspotlight.devspotlight.dto.UserDTO;
import com.devspotlight.devspotlight.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDTO> createUser(UserDTO request);

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUserById(Long userId);

    Optional<UserDTO> findByUsername(String username);
}
