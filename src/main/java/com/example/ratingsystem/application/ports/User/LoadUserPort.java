package com.example.ratingsystem.application.ports.User;

import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import com.example.ratingsystem.domain.models.User;

import java.util.List;
import java.util.Optional;

public interface LoadUserPort {
    Optional<User> loadById(Integer id);

    UserEntity loadEntityById(Integer id);

    List<User> loadByFullName(String fullName);

    User loadByEmail(String email);

    UserEntity loadEntityByEmail(String email);

    List<User> loadPendingUsers();
}
