package com.devspotlight.devspotlight.model;

import com.devspotlight.devspotlight.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "github_profile_link", nullable = false, unique = true)
    // conferir essa anotação de manytoone
    // @ManyToOne
    private String githubProfileLink;

    @Column(name = "github_profile_photo")
    private String githubProfilePhoto;

    @OneToMany(mappedBy = "user")
    private List<Project> repositories;

    //   @OneToMany(mappedBy = "user")
//    private List<Followers> followers;
    private Integer followers;

    @OneToMany(mappedBy = "user")
    private List<FavRepositories> favoritesRepositories;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(com.devspotlight.devspotlight.record.UserDTO data) {
        this.name = data.name();
        this.email = data.email();
        this.favoritesRepositories = data.favoritesRepositories();
        this.followers = data.followers();
        this.role = data.role();
        this.githubProfileLink = data.githubProfileLink();
        this.githubProfilePhoto = data.githubProfilePhoto();
        this.username = data.username();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
