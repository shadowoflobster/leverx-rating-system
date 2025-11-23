package com.example.ratingsystem.adapters.outbound.persistence.mappers;

import com.example.ratingsystem.adapters.outbound.persistence.entities.GameEntity;
import com.example.ratingsystem.domain.models.Game;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {
    public Game entityToDomain(GameEntity entity) {
        return new Game();
    }
}
