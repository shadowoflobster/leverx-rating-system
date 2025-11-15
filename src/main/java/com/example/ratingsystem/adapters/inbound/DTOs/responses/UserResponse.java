package com.example.ratingsystem.adapters.inbound.DTOs.responses;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class UserResponse {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final LocalDateTime createdAt;
    private final String role;
    private final List<Integer> gameIds;
    private final List<Integer> gameObjectIds;
}
