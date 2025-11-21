package com.example.ratingsystem.application.services;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.GameObjectRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.GameObjectResponse;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.GameObjectMapper;
import com.example.ratingsystem.application.ports.GameObject.AddGameObjectPort;
import com.example.ratingsystem.domain.models.GameObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameObjectService {
    private AddGameObjectPort addGameObjectPort;
    private GameObjectMapper gameObjectMapper;


    public GameObjectResponse addGameObject(GameObjectRequest request) {
        GameObject gameObject = addGameObjectPort.add(request);

        return gameObjectMapper.domainToResponse(gameObject);
    }
}
