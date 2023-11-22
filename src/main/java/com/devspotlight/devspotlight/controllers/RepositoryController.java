package com.devspotlight.devspotlight.controllers;

import com.devspotlight.devspotlight.dto.RepositoryDTO;
import com.devspotlight.devspotlight.model.Repository;
import com.devspotlight.devspotlight.model.User;
import com.devspotlight.devspotlight.repository.RepositoryRepository;
import com.devspotlight.devspotlight.repository.UserRepository;
import com.devspotlight.devspotlight.service.RepositoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.util.BeanDefinitionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.beans.Beans;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/repository")
public class RepositoryController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<RepositoryDTO> createRepositoryController(@RequestBody @Valid RepositoryDTO request) {
        Optional<RepositoryDTO> response = repositoryService.createRepository(request);

        return response.map(repositoryDTO -> new ResponseEntity<>(repositoryDTO, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<RepositoryDTO>> getAllRepositoriesByUserController(@PathVariable("userId") Long userID) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()) {
            List<RepositoryDTO> response = repositoryService.getAllRepositoriesByUser(userID);
            return ResponseEntity.ok(response);
        }

        return (ResponseEntity<List<RepositoryDTO>>) ResponseEntity.status(HttpStatus.NOT_FOUND);
    }
}
