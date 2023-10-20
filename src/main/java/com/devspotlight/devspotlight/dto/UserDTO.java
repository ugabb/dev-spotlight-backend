package com.devspotlight.devspotlight.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class UserDTO {

    private Long id;

    @NotBlank
}
