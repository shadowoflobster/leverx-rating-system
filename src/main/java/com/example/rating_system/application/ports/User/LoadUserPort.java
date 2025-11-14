package com.example.rating_system.application.ports.User;

import com.example.rating_system.domain.models.User;

import java.util.List;
import java.util.Optional;

public interface LoadUserPort {
    Optional<User> loadById(Integer id);

    List<User> loadByName(String firstName);

}
