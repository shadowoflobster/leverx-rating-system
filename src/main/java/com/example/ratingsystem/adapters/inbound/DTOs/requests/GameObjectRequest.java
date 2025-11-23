package com.example.ratingsystem.adapters.inbound.DTOs.requests;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameObjectRequest {
    private String title;
    private String description;
    private String gameTitle;
}
