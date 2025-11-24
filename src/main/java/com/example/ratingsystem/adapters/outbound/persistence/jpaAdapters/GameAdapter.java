package com.example.ratingsystem.adapters.outbound.persistence.jpaAdapters;

import com.example.ratingsystem.adapters.outbound.persistence.entities.GameEntity;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.GameMapper;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaGameRepository;
import com.example.ratingsystem.application.ports.Game.AddGamePort;
import com.example.ratingsystem.application.ports.Game.LoadGamePort;
import com.example.ratingsystem.domain.models.Game;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GameAdapter implements AddGamePort, LoadGamePort {
    private final JpaGameRepository gameRepository;
    private final GameMapper gameMapper;

    @Override
    public Game add(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }

        GameEntity entity = gameMapper.domainToEntity(game);
        GameEntity saved = gameRepository.save(entity);

        return gameMapper.entityToDomain(saved);
    }

    @Override
    public Game loadById(Integer id) {
        GameEntity entity = gameRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No game found with id"));
        return gameMapper.entityToDomain(entity);
    }

    @Override
    public List<Game> loadAll() {
        List<GameEntity> gameEntities = gameRepository.findAll();

        return gameEntities.stream()
                .map(gameMapper::entityToDomain)
                .toList();
    }
}
