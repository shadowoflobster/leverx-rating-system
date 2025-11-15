package com.example.ratingsystem.application.ports.Game;

import com.example.ratingsystem.domain.models.Game;

import java.util.List;
import java.util.Optional;

public interface LoadGamePort {
    Optional<Game> loadById(Integer id);

    List<Game> loadAll();
}
