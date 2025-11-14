package com.example.rating_system.domain.models;

import com.example.rating_system.domain.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private UserRole role;
    private ArrayList<Game> games;
    private ArrayList<GameObject> gameObjects;

    private User(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;

        this.createdAt = builder.createdAt != null ? builder.createdAt : LocalDateTime.now();
        this.role = builder.role != null ? builder.role : UserRole.USER;

        this.games = builder.games != null ? builder.games : new ArrayList<>();
        this.gameObjects = builder.gameObjects != null ? builder.gameObjects : new ArrayList<>();
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name shouldn't be empty");
        }
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        this.firstName = firstName.trim();
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        this.email = email.trim();
    }

    public void setPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = (role == null) ? UserRole.USER : role;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games != null ? games : new ArrayList<>();
    }

    public void setGameObjects(ArrayList<GameObject> gameObjects) {
        this.gameObjects = gameObjects != null ? gameObjects : new ArrayList<>();
    }

    public void addGame(Game game) {
        this.games.add(game);
    }

    public void addGameObject(GameObject obj) {
        this.gameObjects.add(obj);
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private LocalDateTime createdAt;
        private UserRole role;
        private ArrayList<Game> games;
        private ArrayList<GameObject> gameObjects;

        public Builder firstName(String firstName) {
            if (firstName == null || firstName.isBlank()) {
                throw new IllegalArgumentException("First name shouldn't be empty");
            }
            this.firstName = firstName.trim();
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName != null ? lastName.trim() : null;
            return this;
        }

        public Builder email(String email) {
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Email is required");
            }
            this.email = email.trim();
            return this;
        }

        public Builder password(String password) {
            if (password == null || password.isBlank()) {
                throw new IllegalArgumentException("Password is required");
            }
            this.password = password;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder role(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder games(ArrayList<Game> games) {
            this.games = games;
            return this;
        }

        public Builder gameObjects(ArrayList<GameObject> gameObjects) {
            this.gameObjects = gameObjects;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}



