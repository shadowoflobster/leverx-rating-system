package com.example.rating_system.application.ports.User;

import com.example.rating_system.domain.models.User;

public interface SaveUserPort {
    void save(User user);
}
