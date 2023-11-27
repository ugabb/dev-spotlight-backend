package com.devspotlight.devspotlight.service;

import com.devspotlight.devspotlight.dto.ProjectDTO;
import com.devspotlight.devspotlight.dto.TechnologiesDTO;
import com.devspotlight.devspotlight.model.Project;
import com.devspotlight.devspotlight.model.Technologies;
import com.devspotlight.devspotlight.repository.ProjectRepository;
import com.devspotlight.devspotlight.repository.TechnologiesRepository;
import com.devspotlight.devspotlight.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TechnologiesRepository technologiesRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<ProjectDTO> createRepository(ProjectDTO request) {
        // incompleto
        Project repo = mapper.map(request, Project.class);
        Project savedRepo = projectRepository.saveAndFlush(repo);

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
            tech.setProject(repo);
            technologiesRepository.saveAndFlush(tech);
        }


        Optional<Project> newRepoOptional = projectRepository.findById(repoId);
        if (newRepoOptional.isPresent()) {
            Project newRepo = newRepoOptional.get();
            ProjectDTO response = mapper.map(newRepo, ProjectDTO.class);
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
    public List<ProjectDTO> getAllRepositoriesByUser(Long userId) {
        List<Project> repos = projectRepository.findByUserId(userId);
        return repos.stream()
                .map(repo -> mapper.map(repo, ProjectDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProjectDTO> getProjectByUserAndId(Long userId, Long projectId) {
        List<Project> projects = projectRepository.findByUserId(userId);
        Optional<Project> projectById = projects.stream().filter(project -> Objects.equals(project.getId(), projectId)).findFirst();
        ProjectDTO projectDTO = mapper.map(projectById, ProjectDTO.class);

        System.out.println(projectDTO);

        return Optional.of(projectDTO);
    }
}
