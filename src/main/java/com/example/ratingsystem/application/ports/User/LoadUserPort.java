package com.example.ratingsystem.application.ports.User;

import com.example.ratingsystem.domain.models.User;

import java.util.List;
import java.util.Optional;

public interface LoadUserPort {
    Optional<User> loadById(Integer id);

    List<User> loadByFullName(String fullName);

}
