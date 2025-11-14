package com.example.rating_system.domain.models;

import lombok.Setter;

import java.time.LocalDateTime;

public class GameObject {
    private Integer id;               // auto-generated
    private String title;
    private String description;
    @Setter
    private User user;
    @Setter
    private Game game;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private GameObject(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.user = builder.user;
        this.game = builder.game;
        this.createdAt = builder.createdAt != null ? builder.createdAt : LocalDateTime.now();
        this.updatedAt = builder.updatedAt != null ? builder.updatedAt : this.createdAt;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title.trim();
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
    }

    public void setUser(User user) {
        if (user == null) throw new IllegalArgumentException("User is required");
        this.user = user;
    }

    public void setGame(Game game) {
        if (game == null) throw new IllegalArgumentException("Game is required");
        this.game = game;
    }


    public static class Builder {
        private String title;
        private String description;
        private User user;
        private Game game;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder title(String title) {
            if (title == null || title.isBlank()) {
                throw new IllegalArgumentException("Title cannot be empty");
            }
            this.title = title.trim();
            return this;
        }

        public Builder description(String description) {
            this.description = description != null ? description.trim() : null;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder game(Game game) {
            this.game = game;
            return this;
        }


        public GameObject build() {
            return new GameObject(this);
        }
    }
}
