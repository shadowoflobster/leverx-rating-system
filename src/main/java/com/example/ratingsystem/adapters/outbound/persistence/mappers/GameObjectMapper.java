package com.example.ratingsystem.adapters.outbound.persistence.mappers;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.GameObjectRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.GameObjectResponse;
import com.example.ratingsystem.adapters.outbound.persistence.entities.GameEntity;
import com.example.ratingsystem.adapters.outbound.persistence.entities.GameObjectEntity;
import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import com.example.ratingsystem.domain.models.GameObject;
import com.example.ratingsystem.domain.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class GameObjectMapper {
    private UserMapper userMapper;
    private GameMapper gameMapper;

    public GameObjectResponse domainToResponse(GameObject domain) {
        GameObjectResponse response = new GameObjectResponse();

        response.setTitle(domain.getTitle());
        response.setDescription(domain.getDescription());
        response.setUserId(domain.getUser().getId());
        response.setGameTitle(domain.getGame().getTitle());

        return response;
    }

    public GameObjectEntity requestToEntity(GameObjectRequest request, UserEntity user, GameEntity game) {
        if (request == null) {
            throw new IllegalArgumentException("GameObjectRequest cannot be null");
        }

        GameObjectEntity entity = new GameObjectEntity();
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setUser(user);
        entity.setGame(game);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }

    public GameObject entityToDomain(GameObjectEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("GameObjectEntity cannot be null");
        }

        GameObject gameObject = GameObject.builder()
                .title(entity.getTitle())
                .description(entity.getDescription())
                .user(entity.getUser() != null ? userMapper.entityToDomain(entity.getUser()) : null)
                .game(entity.getGame() != null ? gameMapper.entityToDomain(entity.getGame()) : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();

        gameObject.setId(entity.getId());

        return gameObject;
    }

    public GameObject requestToDomain(GameObjectRequest request, User user, com.example.ratingsystem.domain.models.Game game) {
        if (request == null) {
            throw new IllegalArgumentException("GameObjectRequest cannot be null");
        }

        return GameObject.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .game(game)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .approved(false)
                .build();
    }

}
