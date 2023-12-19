package com.devspotlight.devspotlight.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "repository")
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_repository")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "link_repository")
    private String linkRepo;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL)
    private List<Technologies> technologies;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectImage> projectImages;

    @Column(name = "likes")
    private Integer likes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
