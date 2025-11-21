package com.example.ratingsystem.adapters.outbound.persistence.jpaAdapters;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.GameObjectRequest;
import com.example.ratingsystem.adapters.outbound.persistence.entities.GameEntity;
import com.example.ratingsystem.adapters.outbound.persistence.entities.GameObjectEntity;
import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.GameObjectMapper;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaGameObjectRepository;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaGameRepository;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaUserRepository;
import com.example.ratingsystem.application.ports.GameObject.AddGameObjectPort;
import com.example.ratingsystem.domain.models.GameObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GameObjectAdapter implements AddGameObjectPort {
    private JpaGameObjectRepository gameObjectRepository;
    private JpaUserRepository userRepository;
    private JpaGameRepository gameRepository;
    private GameObjectMapper gameObjectMapper;

    public GameObject add(GameObjectRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Game object cannot be null");
        }

        UserEntity user = null;
        if (request.getUserId() != null) {
            user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
        }

        GameEntity game = null;
        if (request.getGameId() != null) {
            game = gameRepository.findById(request.getGameId())
                    .orElseThrow(() -> new IllegalArgumentException("Game not found"));
        }

        GameObjectEntity entity = gameObjectMapper.requestToEntity(request, user, game);

        GameObjectEntity saved = gameObjectRepository.save(entity);

        return gameObjectMapper.entityToDomain(saved);
    }
}
