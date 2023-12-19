package com.devspotlight.devspotlight.service;

import com.devspotlight.devspotlight.dto.UserDTO;
import com.devspotlight.devspotlight.model.User;
import com.devspotlight.devspotlight.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Optional<UserDTO> createUser(UserDTO request) {
        User user = mapper.map(request, User.class);
        UserDTO response = mapper.map(user, UserDTO.class);
        userRepository.saveAndFlush(user);
        return Optional.of(response);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> responsesUserDTO = new ArrayList<>();

        users.forEach(user -> {
            UserDTO response = mapper.map(user, UserDTO.class);
            responsesUserDTO.add(response);
        });
        return responsesUserDTO;
    }

    @Override
    public String getUserRepositoryLink() {
        return null;
    }

    @Override
    public Optional<UserDTO> getUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(u -> mapper.map(u, UserDTO.class));
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(u -> mapper.map(u, UserDTO.class));
    }

}