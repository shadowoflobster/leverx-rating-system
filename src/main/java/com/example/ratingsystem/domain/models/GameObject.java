package com.example.ratingsystem.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GameObject {
    private Integer id;
    private String title;
    private String description;
    private User user;
    private Game game;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public GameObject(String title,
                      String description,
                      User user,
                      Game game,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt) {

        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title required");
        if (user == null) throw new IllegalArgumentException("User required");
        if (game == null) throw new IllegalArgumentException("Game required");

        this.title = title.trim();
        this.user = user;
        this.game = game;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : this.createdAt;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title.trim();
        this.updatedAt = LocalDateTime.now();
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
        this.updatedAt = LocalDateTime.now();
    }

    public void setUser(User user) {
        if (user == null) throw new IllegalArgumentException("User is required");
        this.user = user;
        this.updatedAt = LocalDateTime.now();
    }

    public void setGame(Game game) {
        if (game == null) throw new IllegalArgumentException("Game is required");
        this.game = game;
        this.updatedAt = LocalDateTime.now();
    }


}
