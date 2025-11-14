package com.example.rating_system.application.ports.Game;

import com.example.rating_system.domain.models.Game;

import java.util.List;
import java.util.Optional;

public interface LoadGamePort {
    Optional<Game> loadById(Integer id);

    List<Game> loadAll();
}
