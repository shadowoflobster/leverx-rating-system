package com.example.ratingsystem.adapters.inbound.DTOs.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameObjectRequest {
    private String title;
    private String description;
    private String gameTitle;
}
