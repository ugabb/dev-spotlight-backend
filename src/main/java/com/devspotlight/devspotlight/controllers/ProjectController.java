package com.devspotlight.devspotlight.controllers;

import com.devspotlight.devspotlight.dto.ProjectDTO;
import com.devspotlight.devspotlight.model.Project;
import com.devspotlight.devspotlight.model.User;
import com.devspotlight.devspotlight.repository.UserRepository;
import com.devspotlight.devspotlight.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<ProjectDTO> createRepositoryController(@RequestBody @Valid ProjectDTO request) {
        Optional<ProjectDTO> response = projectService.createRepository(request);

        return response.map(projectDTO -> new ResponseEntity<>(projectDTO, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectDTO>> getAllRepositoriesByUserController(@PathVariable("userId") Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<ProjectDTO> response = projectService.getAllRepositoriesByUser(userId);
            return ResponseEntity.ok(response);
        }

        return (ResponseEntity<List<ProjectDTO>>) ResponseEntity.status(HttpStatus.NOT_FOUND);
    }

    // is workin but technologies repo_id is null
    @GetMapping("/user/{userId}/project/{projectId}")
    public ResponseEntity<Optional<ProjectDTO>> getProjectByUserAndId(@PathVariable("userId") Long userId, @PathVariable("projectId") Long projectId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<ProjectDTO> response = projectService.getProjectByUserAndId(userId, projectId);
            if (response.isPresent()) {
                return ResponseEntity.ok(response);
            }
        }

        return (ResponseEntity<Optional<ProjectDTO>>) ResponseEntity.status(HttpStatus.NOT_FOUND);
    }
}
