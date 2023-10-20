package com.devspotlight.devspotlight.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private  String password;

    @Column(name = "github_profile_link", nullable = false)
    // conferir essa anotação de manytoone
    // @ManyToOne
    private String githubProfileLink;

    @Column(name = "github_profile_photo")
    private String githubProfilePhoto;

    @Column(name = "followers")
    private List<Followers> followers;

    @Column(name = "favorites_repositories")
    private List<FavRepositories> favoritesRepositories;
}
