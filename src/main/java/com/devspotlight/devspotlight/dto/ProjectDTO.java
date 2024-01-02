package com.devspotlight.devspotlight.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDTO {
    private Long id;
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String description;
    @NotBlank
    @NotNull
    private String linkRepo;

    private String deployedLink;

    private List<TechnologiesDTO> technologies;

    private List<ProjectImageDTO> projectImages;

    private Integer likes;
    @NotBlank
    private Long userId;
}
