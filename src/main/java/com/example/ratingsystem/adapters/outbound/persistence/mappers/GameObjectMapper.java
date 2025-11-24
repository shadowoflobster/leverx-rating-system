package com.example.ratingsystem.adapters.outbound.persistence.mappers;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.GameObjectRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.GameObjectResponse;
import com.example.ratingsystem.adapters.outbound.persistence.entities.GameEntity;
import com.example.ratingsystem.adapters.outbound.persistence.entities.GameObjectEntity;
import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import com.example.ratingsystem.domain.models.GameObject;
import com.example.ratingsystem.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class GameObjectMapper {
    private final UserMapper userMapper;
    private final GameMapper gameMapper;


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

    public GameObjectEntity domainToEntity(GameObject domain) {
        if (domain == null) {
            throw new IllegalArgumentException("GameObject domain cannot be null");
        }

        GameObjectEntity entity = new GameObjectEntity();

        entity.setId(domain.getId());

        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setApproved(domain.isApproved());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.now());

        if (domain.getUser() != null) {
            entity.setUser(userMapper.domainToEntity(domain.getUser()));
        }

        if (domain.getGame() != null) {
            entity.setGame(gameMapper.domainToEntity(domain.getGame()));
        }

        return entity;
    }


}
