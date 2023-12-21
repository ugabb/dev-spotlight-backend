package com.devspotlight.devspotlight.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TechnologiesDTO {
    @NotNull
    @NotBlank
    private String name;
//    private String icon;

}
