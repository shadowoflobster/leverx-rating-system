package com.example.rating_system.domain.models;

import com.example.rating_system.domain.enums.UserRole;
import lombok.Builder;
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

    @Builder
    public User(String firstName,
                String lastName,
                String email,
                String password,
                LocalDateTime createdAt,
                UserRole role,
                ArrayList<Game> games,
                ArrayList<GameObject> gameObjects) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.role = role;
        this.games = games;
        this.gameObjects = gameObjects;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name shouldn't be empty");
        }
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        this.firstName = lastName.trim();
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
}