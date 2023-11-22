package com.devspotlight.devspotlight.dto;

import com.devspotlight.devspotlight.model.Technologies;
import com.devspotlight.devspotlight.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RepositoryDTO {
    @NotBlank
    @NotNull
    private String name;
    @NotBlank
    @NotNull
    private String description;
    @NotBlank
    @NotNull
    private String linkRepo;

    private List<TechnologiesDTO> technologies;
    private Integer likes;
    @NotBlank
    private Long userId;

}
