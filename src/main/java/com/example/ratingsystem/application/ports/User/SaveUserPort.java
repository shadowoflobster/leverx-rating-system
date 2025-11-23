package com.example.ratingsystem.application.ports.User;

import com.example.ratingsystem.domain.models.User;

public interface SaveUserPort {
    User save(User user);
}
