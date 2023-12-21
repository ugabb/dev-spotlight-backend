package com.devspotlight.devspotlight.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "project_image")
@Data
public class ProjectImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_project_image")
    private Long id;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
