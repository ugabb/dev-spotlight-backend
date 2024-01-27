package com.devspotlight.devspotlight.record;

import com.devspotlight.devspotlight.model.FavRepositories;
import com.devspotlight.devspotlight.model.Role;

import java.util.List;

public record UserDTO(String name, String username, String email, String githubProfileLink, String githubProfilePhoto, Integer followers, List<FavRepositories> favoritesRepositories, Role role) {
}
