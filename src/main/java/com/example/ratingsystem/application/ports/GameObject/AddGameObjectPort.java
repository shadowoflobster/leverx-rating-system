package com.example.ratingsystem.application.ports.GameObject;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.GameObjectRequest;
import com.example.ratingsystem.domain.models.GameObject;

public interface AddGameObjectPort {
    GameObject add(GameObjectRequest gameObjectRequest);
}
