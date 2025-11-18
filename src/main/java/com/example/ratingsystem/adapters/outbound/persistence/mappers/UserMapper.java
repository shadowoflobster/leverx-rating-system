package com.example.ratingsystem.adapters.outbound.persistence.mappers;

import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import com.example.ratingsystem.domain.models.User;

import java.util.ArrayList;

public class UserMapper {
    public User entityToDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .createdAt(entity.getCreatedAt())
                .role(entity.getRole())
                .games(new ArrayList<>())
                .gameObjects(new ArrayList<>())
                .build();
    }

    public UserEntity domainToEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity entity = new UserEntity();

        entity.setEmail(user.getEmail());
        entity.setFirstName(user.getFirstName());
        if (user.getLastName() != null && !user.getLastName().isBlank()) {
            entity.setLastName(user.getLastName());
        }
        entity.setPassword(user.getPassword());
        entity.setGameObjects(new ArrayList<>());
        entity.setGivenComments(new ArrayList<>());
        entity.setTakenComments(new ArrayList<>());
        entity.setGivenRating(new ArrayList<>());
        entity.setTakenRating(new ArrayList<>());

        return entity;
    }
}
