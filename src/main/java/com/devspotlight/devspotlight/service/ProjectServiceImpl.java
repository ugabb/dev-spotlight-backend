package com.devspotlight.devspotlight.service;

import com.devspotlight.devspotlight.dto.ProjectDTO;
import com.devspotlight.devspotlight.dto.ProjectImageDTO;
import com.devspotlight.devspotlight.dto.TechnologiesDTO;
import com.devspotlight.devspotlight.exceptions.ProjectNotFoundException;
import com.devspotlight.devspotlight.exceptions.UserNotFoundException;
import com.devspotlight.devspotlight.model.Project;
import com.devspotlight.devspotlight.model.ProjectImage;
import com.devspotlight.devspotlight.model.Technologies;
import com.devspotlight.devspotlight.model.User;
import com.devspotlight.devspotlight.repository.ProjectImagesRepository;
import com.devspotlight.devspotlight.repository.ProjectRepository;
import com.devspotlight.devspotlight.repository.TechnologiesRepository;
import com.devspotlight.devspotlight.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    @Autowired
    private ProjectImagesRepository projectImagesRepository;

    @Override
    @Transactional  // Ensure a new transaction for the entire method
    public Optional<ProjectDTO> createProject(ProjectDTO request) {
        // Step 1: Create a new Project entity from ProjectDTO
        Project projectEntity = mapper.map(request, Project.class);

        // Step 2: Save the new Project entity to obtain its ID
        Project savedProjectEntity = projectRepository.saveAndFlush(projectEntity);
        Long projectId = savedProjectEntity.getId();

        // Step 3: Fetch the saved Project entity along with Technologies and ProjectImages
        Optional<Project> newRepoOptional = projectRepository.findById(projectId);

        if (newRepoOptional.isPresent()) {
            Project newRepo = newRepoOptional.get();

            // Step 4: Set project_id in Technologies and ProjectImage entities
            List<TechnologiesDTO> technologiesDTOList = request.getTechnologies();
            List<ProjectImageDTO> projectImageDTOList = request.getProjectImages();

            if (technologiesDTOList != null) {
                for (TechnologiesDTO technologiesDTO : technologiesDTOList) {
                    Technologies technologiesEntity = mapper.map(technologiesDTO, Technologies.class);
                    technologiesEntity.setProject(newRepo); // Set the Project entity
                    technologiesRepository.save(technologiesEntity);
                }
            }

            if (projectImageDTOList != null) {
                for (ProjectImageDTO projectImageDTO : projectImageDTOList) {
                    ProjectImage projectImageEntity = mapper.map(projectImageDTO, ProjectImage.class);
                    projectImageEntity.setProject(newRepo); // Set the Project entity
                    projectImagesRepository.save(projectImageEntity);
                }
            }

            // Step 5: Map the result to ProjectDTO
            ProjectDTO response = mapper.map(newRepo, ProjectDTO.class);
            return Optional.of(response);
        } else {
            // Handle the case where the repository was not found
            return Optional.empty();
        }
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projects.stream().map(project -> {
            // Fetch technologies and projectImages explicitly
//            List<Technologies> technologies = technologiesRepository.findByProject(project);
//            List<ProjectImage> projectImages = projectImageRepository.findByProject(project);

            // Map Project, Technologies, and ProjectImages to ProjectDTO
            ProjectDTO projectDTO = mapper.map(project, ProjectDTO.class);
//            projectDTO.setTechnologies(mapper.map(technologies, new ArrayList<TechnologiesDTO>().getClass()));
//            projectDTO.setProjectImages(mapper.map(projectImages, new ArrayList<ProjectImageDTO>().getClass()));

            return projectDTO;
        }).collect(Collectors.toList());
    }



    @Override
    public List<ProjectDTO> getAllRepositoriesByUser(Long userId) {
        List<Project> projects = projectRepository.findByUserId(userId);
        System.out.println(projects);
        return projects.stream()
                .map(proj -> {
                    ProjectDTO projectDTO = mapper.map(proj, ProjectDTO.class);
//                    proj.getTechnologies().forEach(tech -> {
//                        TechnologiesDTO technologiesDTO = mapper.map(tech, TechnologiesDTO.class);
//                        System.out.println(technologiesDTO);
//                        technologiesDTO.setProject_id(proj.getId());
//                        if(Objects.equals(technologiesDTO.getProject_id(), projectDTO.getId())){
//                            projectDTO.getTechnologies().add(technologiesDTO);
//                        }
//                    });
//
//                    proj.getProjectImages().forEach(img -> {
//                        ProjectImageDTO projectImageDTO = mapper.map(img, ProjectImageDTO.class);
//                        projectImageDTO.setProject_id(proj.getId());
//                        projectDTO.getProjectImages().add(projectImageDTO);
//                    });

                    return projectDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProjectDTO> getProjectByUserIAndProjectName(Long userId, String projectName) throws UserNotFoundException, ProjectNotFoundException {
        // verifica se usuario existe
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new UserNotFoundException("User not found with id:" + userId);

        // verifica se usuario tem projetos existe
        List<Project> projects = projectRepository.findByUserId(userId);
        if(projects.isEmpty()) throw new ProjectNotFoundException("Project Not Found");

        // verifica se o projeto existe
        Optional<Project> project = projects.stream().filter(proj -> Objects.equals(proj.getName(), projectName)).findFirst();
        if(project.isPresent()){
            ProjectDTO projectDTO = mapper.map(project, ProjectDTO.class);
            return Optional.of(projectDTO);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProjectDTO> getProjectByUserAndId(Long userId, Long projectId) {
        List<Project> projects = projectRepository.findByUserId(userId);
        Optional<Project> projectById = projects.stream().filter(project -> Objects.equals(project.getId(), projectId)).findFirst();
        ProjectDTO projectDTO = mapper.map(projectById, ProjectDTO.class);

        System.out.println(projectDTO);

        return Optional.of(projectDTO);
    }

    @Override
    public void deleteProjectById(Long id) {
        Optional<Project> project = projectRepository.findById(id);

        if(project.isPresent()){
            projectRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("Project not found with id: " + id);
        }
    }
}
