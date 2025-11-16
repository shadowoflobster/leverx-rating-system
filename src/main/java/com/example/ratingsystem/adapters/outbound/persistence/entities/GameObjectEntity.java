package com.example.ratingsystem.adapters.outbound.persistence.entities;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_objects")
public class GameObjectEntity {
    @Id
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
