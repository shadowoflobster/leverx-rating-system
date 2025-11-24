package com.example.ratingsystem.application.services;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.GameObjectRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.GameObjectResponse;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.GameObjectMapper;
import com.example.ratingsystem.application.ports.GameObject.AddGameObjectPort;
import com.example.ratingsystem.application.ports.GameObject.DeleteGameObjectPort;
import com.example.ratingsystem.application.ports.GameObject.LoadGameObjectPort;
import com.example.ratingsystem.application.ports.GameObject.UpdateGameObjectPort;
import com.example.ratingsystem.domain.models.GameObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameObjectService {
    private AddGameObjectPort addGameObjectPort;
    private LoadGameObjectPort loadGameObjectPort;
    private DeleteGameObjectPort deleteGameObjectPort;
    private UpdateGameObjectPort updateGameObjectPort;
    private GameObjectMapper gameObjectMapper;


    public GameObjectResponse addGameObject(String email, GameObjectRequest request) {

        GameObject gameObject = addGameObjectPort.add(email, request);

        return gameObjectMapper.domainToResponse(gameObject);
    }

    public Optional<GameObject> getById(Integer id) {
        return loadGameObjectPort.loadById(id);
    }

    public List<GameObject> getBySellerId(Integer sellerId) {
        return loadGameObjectPort.loadBySellerId(sellerId);
    }

    public List<GameObject> getByGameId(Integer gameId) {
        return loadGameObjectPort.loadByGameId(gameId);
    }

    public List<GameObject> getByName(String name) {
        return loadGameObjectPort.loadByName(name);
    }

    public void deleteById(Integer id) {
        deleteGameObjectPort.deleteById(id);
    }

    public GameObject updateById(Integer id, GameObjectRequest request) {
        return updateGameObjectPort.updateById(id, request);
    }
}
