package com.devspotlight.devspotlight.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "repository")
public class Repository {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_repository")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "link_repository",nullable = false)
    private String linkRepo;

    @Column(name = "technologies",nullable = false)
    private List<Technologies> technologies;

    @Column(name = "images")
    private List<> images;

    @Column(name = "likes")
    private Integer likes;
}
