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
        // incompleto
        Repository repo = mapper.map(request, Repository.class);
        Repository savedRepo = repository.saveAndFlush(repo);

        Long repoId = savedRepo.getId();

        List<TechnologiesDTO> technologiesDTOList = request.getTechnologies();
        for (TechnologiesDTO techDto : technologiesDTOList) {
            Technologies tech = mapper.map(techDto, Technologies.class);

            // Set the repositoryId in TechnologiesDTO
            techDto.setRepository_id(repoId); // Setting repository ID in TechnologiesDTO

            // Create a new Repository object and set its ID
//            Repository repoForTech = new Repository();
            repo.setId(repoId);

            // Set the created Repository in Technologies
            tech.setRepository(repo);
            technologiesRepository.saveAndFlush(tech);
        }


        Optional<Repository> newRepoOptional = repository.findById(repoId);
        if (newRepoOptional.isPresent()) {
            Repository newRepo = newRepoOptional.get();
            RepositoryDTO response = mapper.map(newRepo, RepositoryDTO.class);
            for (TechnologiesDTO technologies : response.getTechnologies()) {
                technologies.setRepository_id(repoId);
            }
            return Optional.of(response);
        } else {
            // Handle the case where the repository was not found
            return Optional.empty();
        }
    }


    @Override
    public List<RepositoryDTO> getAllRepositoriesByUser(Long userId) {
        List<Repository> repos = repository.findByUserId(userId);
        return repos.stream()
                .map(repo -> mapper.map(repo, RepositoryDTO.class))
                .collect(Collectors.toList());
    }
}
