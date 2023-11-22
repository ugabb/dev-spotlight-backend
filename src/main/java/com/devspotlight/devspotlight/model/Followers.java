package com.devspotlight.devspotlight.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "followers")
@Data
public class Followers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "follower_photo")
    private String followerPhoto;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
