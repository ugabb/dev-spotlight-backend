package com.devspotlight.devspotlight.dto;

import com.devspotlight.devspotlight.model.FavRepositories;
import com.devspotlight.devspotlight.model.Followers;
import com.devspotlight.devspotlight.model.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {


    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String githubProfileLink;
    private String githubProfilePhoto;
    private List<Followers> followers;
    private List<FavRepositories> favoritesRepositories;
    private Role role;

}
