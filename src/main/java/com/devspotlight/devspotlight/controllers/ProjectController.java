package com.devspotlight.devspotlight.controllers;

import com.devspotlight.devspotlight.dto.ProjectCreationDTO;
import com.devspotlight.devspotlight.dto.ProjectDTO;
import com.devspotlight.devspotlight.exceptions.ProjectNotFoundException;
import com.devspotlight.devspotlight.exceptions.UserNotFoundException;
import com.devspotlight.devspotlight.model.User;
import com.devspotlight.devspotlight.repository.UserRepository;
import com.devspotlight.devspotlight.service.ProjectService;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity<ProjectDTO> createRepositoryController(@RequestBody @Valid ProjectCreationDTO request) {
        Optional<ProjectDTO> response = projectService.createProject(request);

        return response.map(projectDTO -> new ResponseEntity<>(projectDTO, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @PostMapping("/{projectId}/likes/add")
    public ResponseEntity<ProjectDTO> addLikeProjectController(@PathVariable("projectId") Long projectId){
        try{
            ProjectDTO res = projectService.addLikeProject(projectId);
            return ResponseEntity.ok(res);
        } catch (ProjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/{projectId}/likes/remove")
    public ResponseEntity<ProjectDTO> removeLikeProjectController(@PathVariable("projectId") Long projectId){
        try{
            ProjectDTO res = projectService.removeLikeProject(projectId);
            return ResponseEntity.ok(res);
        } catch (ProjectNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjectsController() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{userId}/{projectName}")
    public ResponseEntity<Optional<ProjectDTO>> getProjectByNameController(@PathVariable("userId") Long userId, @PathVariable("projectName") String projectName) {
        try {
            Optional<ProjectDTO> projectDTO = projectService.getProjectByUserIAndProjectName(userId, projectName);
            if(projectDTO.isPresent()) {
                return ResponseEntity.ok(projectDTO);
            } else{
                return ResponseEntity.notFound().build();
            }
        }catch (EntityNotFoundException | UserNotFoundException | ProjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
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

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProjectController(@PathVariable("projectId") Long projectId) {
        try {
            projectService.deleteProjectById(projectId);

            return ResponseEntity.ok("Project deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting project");
        }
    }
}