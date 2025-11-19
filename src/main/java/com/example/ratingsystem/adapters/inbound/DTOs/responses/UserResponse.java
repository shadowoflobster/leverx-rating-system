package com.example.ratingsystem.adapters.inbound.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdAt;
    private String role;
    private List<Integer> gameIds;
    private List<Integer> gameObjectIds;
}