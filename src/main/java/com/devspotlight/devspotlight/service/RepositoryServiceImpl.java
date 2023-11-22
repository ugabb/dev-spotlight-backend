package com.devspotlight.devspotlight.service;

import com.devspotlight.devspotlight.dto.RepositoryDTO;
import com.devspotlight.devspotlight.dto.TechnologiesDTO;
import com.devspotlight.devspotlight.model.Repository;
import com.devspotlight.devspotlight.model.Technologies;
import com.devspotlight.devspotlight.repository.RepositoryRepository;
import com.devspotlight.devspotlight.repository.TechnologiesRepository;
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

    @Autowired
    private TechnologiesRepository technologiesRepository;

    @Override
    public Optional<RepositoryDTO> createRepository(RepositoryDTO request) {

        Repository repo = mapper.map(request, Repository.class);
        Repository savedRepo = repository.saveAndFlush(repo);

        Long repoId = savedRepo.getId();
        System.out.println("REPOSITORY ID:");
        System.out.println(repoId);

        // Set the Repository ID in Technologies
        List<TechnologiesDTO> technologiesDTOList = request.getTechnologies();
        for (TechnologiesDTO techDto : technologiesDTOList) {
            techDto.setRepoId(repoId); // Set Repository with ID
            technologiesRepository.saveAndFlush(mapper.map(techDto, Technologies.class));
        }

        RepositoryDTO response = mapper.map(repo, RepositoryDTO.class);

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
