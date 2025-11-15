package com.example.ratingsystem.application.ports.GameObject;

import com.example.ratingsystem.domain.models.Game;
import com.example.ratingsystem.domain.models.GameObject;

import java.util.List;
import java.util.Optional;

public interface LoadGameObjectPort {
    Optional<GameObject> loadById(Integer id);

    List<GameObject> loadBySellerId(Integer sellerId);

    List<GameObject> loadByGameId(Integer gameId);

    List<GameObject> loadByName(String name);

    List<GameObject> loadByGame(Game game);
}
