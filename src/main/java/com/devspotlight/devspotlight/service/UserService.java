package com.devspotlight.devspotlight.service;

import com.devspotlight.devspotlight.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDTO> createUser(UserDTO request);

    List<UserDTO> getAllUsers();
}
