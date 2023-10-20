package com.devspotlight.devspotlight.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite_repositories")
public class FavRepositories {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "link_repository",nullable = false)
    private String link;

    @Column(name = "author_username",nullable = false)
    private String author;
}
