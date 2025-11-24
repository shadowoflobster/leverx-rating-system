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
import com.example.ratingsystem.application.ports.GameObject.DeleteGameObjectPort;
import com.example.ratingsystem.application.ports.GameObject.LoadGameObjectPort;
import com.example.ratingsystem.application.ports.GameObject.UpdateGameObjectPort;
import com.example.ratingsystem.domain.models.GameObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GameObjectAdapter implements AddGameObjectPort, LoadGameObjectPort, DeleteGameObjectPort, UpdateGameObjectPort {
    private JpaGameObjectRepository gameObjectRepository;
    private JpaUserRepository userRepository;
    private JpaGameRepository gameRepository;
    private GameObjectMapper gameObjectMapper;

    @Override
    public GameObject add(String authorEmail, GameObjectRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Game object cannot be null");
        }

        UserEntity user = null;
        if (authorEmail != null) {
            user = userRepository.findByEmail(authorEmail)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
        }

        if (request.getGameTitle() == null) {
            throw new IllegalArgumentException("Game title cannot be null");
        }

        GameEntity game = gameRepository.findByTitle(request.getGameTitle())
                .orElseGet(() -> {
                    GameEntity newGame = new GameEntity();
                    newGame.setTitle(request.getGameTitle());
                    return gameRepository.save(newGame);
                });

        GameObjectEntity entity = gameObjectMapper.requestToEntity(request, user, game);

        entity.setApproved(false);

        GameObjectEntity saved = gameObjectRepository.save(entity);

        return gameObjectMapper.entityToDomain(saved);
    }

    @Override
    public Optional<GameObject> loadById(Integer id) {
        return gameObjectRepository.findById(id)
                .map(gameObjectMapper::entityToDomain);
    }

    @Override
    public List<GameObject> loadBySellerId(Integer sellerId) {
        return gameObjectRepository.findByUserId(sellerId)
                .stream()
                .map(gameObjectMapper::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<GameObject> loadByGameId(Integer gameId) {
        return gameObjectRepository.findByGameId(gameId)
                .stream()
                .map(gameObjectMapper::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<GameObject> loadByName(String name) {
        return gameObjectRepository.findByTitleContainingIgnoreCase(name)
                .stream()
                .map(gameObjectMapper::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<GameObject> loadPendingObjects() {
        return gameObjectRepository.findByApprovedFalse().stream()
                .map(gameObjectMapper::entityToDomain)
                .toList();
    }


    @Override
    public void deleteById(Integer id) {
        gameObjectRepository.deleteById(id);
    }

    @Override
    public GameObject updateById(Integer id, GameObjectRequest gameObjectRequest) {
        GameObjectEntity entity = gameObjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("GameObject not found with id: " + id));

        entity.setTitle(gameObjectRequest.getTitle());
        entity.setDescription(gameObjectRequest.getDescription());

        GameObjectEntity saved = gameObjectRepository.save(entity);

        return gameObjectMapper.entityToDomain(saved);
    }

    @Override
    public GameObject update(GameObject domain) {
        GameObjectEntity entity = gameObjectMapper.domainToEntity(domain);
        GameObjectEntity saved = gameObjectRepository.save(entity);
        return gameObjectMapper.entityToDomain(saved);
    }

}
