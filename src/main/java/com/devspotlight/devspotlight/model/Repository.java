package com.devspotlight.devspotlight.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "repository")
@Data
public class Repository {
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

    @OneToMany(mappedBy = "repository",cascade = CascadeType.ALL)
    private List<Technologies> technologies;

//    @Column(name = "images")
//    private List<> images;

    @Column(name = "likes")
    private Integer likes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
