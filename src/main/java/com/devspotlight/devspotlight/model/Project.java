package com.devspotlight.devspotlight.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "project")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_project")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "deployed_link")
    private String deployedLink;

    @Column(name = "link_repository", unique = true)
    private String linkRepo;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Technologies> technologies;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProjectImage> projectImages;


    @Column(name = "likes")
    private Integer likes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
