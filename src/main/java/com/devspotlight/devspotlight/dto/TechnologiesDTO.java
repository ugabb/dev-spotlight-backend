package com.devspotlight.devspotlight.dto;

import com.devspotlight.devspotlight.model.Repository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TechnologiesDTO {
    @NotNull
    @NotBlank
    private String name;
    private String icon;
    @NotBlank
    private Long repoId;
}
