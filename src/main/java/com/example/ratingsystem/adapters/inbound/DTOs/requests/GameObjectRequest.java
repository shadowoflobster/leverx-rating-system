package com.example.ratingsystem.adapters.inbound.DTOs.requests;

import lombok.Value;

@Value
public class GameObjectRequest {
    String title;
    String description;
    String userEmail;
    Integer gameId;
}
