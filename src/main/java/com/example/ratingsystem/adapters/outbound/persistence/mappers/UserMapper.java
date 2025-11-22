package com.example.ratingsystem.adapters.outbound.persistence.mappers;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.UserRequest;
import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import com.example.ratingsystem.domain.enums.UserRole;
import com.example.ratingsystem.domain.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@AllArgsConstructor
public class UserMapper {
    private PasswordEncoder passwordEncoder;

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

    public UserEntity requestToEntity(UserRequest request) {
        if (request == null) {
            return null;
        }

        UserEntity entity = new UserEntity();

        entity.setEmail(request.getEmail().trim().toLowerCase());
        entity.setFirstName(request.getFirstName().trim());
        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            entity.setLastName(request.getLastName().trim());
        }
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        if (request.getRole() != null && !request.getRole().isBlank()) {
            entity.setRole(UserRole.valueOf(request.getRole()));
        } else {
            entity.setRole(UserRole.Seller);
        }
        entity.setGameObjects(new ArrayList<>());
        entity.setGivenComments(new ArrayList<>());
        entity.setTakenComments(new ArrayList<>());
        entity.setGivenRating(new ArrayList<>());
        entity.setTakenRating(new ArrayList<>());

        return entity;

    }
}
