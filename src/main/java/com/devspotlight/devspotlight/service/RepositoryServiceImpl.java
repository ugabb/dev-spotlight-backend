package com.devspotlight.devspotlight.service;

import com.devspotlight.devspotlight.dto.RepositoryDTO;
import com.devspotlight.devspotlight.model.Repository;
import com.devspotlight.devspotlight.repository.RepositoryRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RepositoryServiceImpl implements RepositoryService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private RepositoryRepository repository;

    @Override
    public Optional<RepositoryDTO> createRepository(RepositoryDTO request) {
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//        Set<ConstraintViolation<RepositoryDTO>> violations = validator.validate(request);
//
//        if(!violations.isEmpty()){
//            throw new RuntimeException("Repository DTO validation failed.");
//        }
        Repository repo = mapper.map(request, Repository.class);
        RepositoryDTO response = mapper.map(repo, RepositoryDTO.class);
        System.out.println("request:");
        System.out.println(repo);
        repository.saveAndFlush(repo);
        return Optional.of(response);
    }

    @Override
    public List<RepositoryDTO> getAllRepositoriesByUser(Long userId) {
        List<Repository> repos = repository.findByUserId(userId);
        return repos.stream()
                .map(repo -> mapper.map(repo, RepositoryDTO.class))
                .collect(Collectors.toList());
    }
}
