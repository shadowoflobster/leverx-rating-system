package com.example.ratingsystem.application.ports.User;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.UserRequest;
import com.example.ratingsystem.domain.models.User;

public interface SaveUserPort {
    User save(UserRequest request);
}
