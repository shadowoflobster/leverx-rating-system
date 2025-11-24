package com.example.ratingsystem.adapters.outbound.persistence.mappers;

import com.example.ratingsystem.adapters.inbound.DTOs.responses.GameResponse;
import com.example.ratingsystem.adapters.outbound.persistence.entities.GameEntity;
import com.example.ratingsystem.domain.models.Game;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GameMapper {

    public Game entityToDomain(GameEntity entity) {
        if (entity == null) return null;

        return Game.builder()
                .title(entity.getTitle())
                .description(entity.getDescription() != null ? entity.getDescription() : "")
                .gameObjects(new ArrayList<>())
                .build();
    }

    public GameEntity domainToEntity(Game game) {
        if (game == null) return null;

        GameEntity entity = new GameEntity();
        entity.setTitle(game.getTitle());
        entity.setDescription(game.getDescription());
        return entity;
    }

    public GameResponse domainToResponse(Game game) {
        if (game == null) return null;

        return new GameResponse(
                game.getId(),
                game.getTitle(),
                game.getDescription() != null ? game.getDescription() : ""
        );
    }
}
