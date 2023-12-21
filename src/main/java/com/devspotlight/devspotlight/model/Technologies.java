package com.devspotlight.devspotlight.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "technologies")
@Data
public class Technologies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "technology_name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}

