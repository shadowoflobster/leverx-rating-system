package com.example.ratingsystem.domain.models;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Game {
    private Integer id;
    private String title;
    private String description;
    private ArrayList<GameObject> gameObjects;

    @Builder
    public Game(String title,
                String description,
                ArrayList<GameObject> gameObjects
    ) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title required");
        this.title = title.trim();
        this.description = description.trim();
        this.gameObjects = gameObjects;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title required");
        this.title = title.trim();
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public void setGameObjects(ArrayList<GameObject> gameObjects) {
        this.gameObjects = gameObjects != null ? gameObjects : new ArrayList<>();
    }

    public void addGameObject(GameObject obj) {
        this.gameObjects.add(obj);
    }

}
