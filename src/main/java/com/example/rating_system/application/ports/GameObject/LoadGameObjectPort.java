package com.example.rating_system.application.ports.GameObject;

import com.example.rating_system.domain.models.Game;
import com.example.rating_system.domain.models.GameObject;

import java.util.List;
import java.util.Optional;

public interface LoadGameObjectPort {
    Optional<GameObject> loadById(Integer id);

    List<GameObject> loadBySellerId(Integer sellerId);

    List<GameObject> loadByGameId(Integer gameId);

    List<GameObject> loadByName(String name);

    List<GameObject> loadByGame(Game game)
}
