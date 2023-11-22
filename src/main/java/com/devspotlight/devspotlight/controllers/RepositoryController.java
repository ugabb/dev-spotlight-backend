package com.devspotlight.devspotlight.controllers;

import com.devspotlight.devspotlight.dto.RepositoryDTO;
import com.devspotlight.devspotlight.model.Repository;
import com.devspotlight.devspotlight.repository.RepositoryRepository;
import com.devspotlight.devspotlight.service.RepositoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.util.BeanDefinitionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.Beans;
import java.util.Optional;

@RestController
@RequestMapping("/repository")
public class RepositoryController {

    @Autowired
    private RepositoryService repositoryService;

    @PostMapping
    public ResponseEntity<RepositoryDTO> createRepositoryController(@RequestBody @Valid RepositoryDTO request) {
        System.out.println("request do controler ====================:");
        System.out.println(request);
        Optional<RepositoryDTO> response = repositoryService.createRepository(request);

        return response.map(repositoryDTO -> new ResponseEntity<>(repositoryDTO, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));


    }
}
