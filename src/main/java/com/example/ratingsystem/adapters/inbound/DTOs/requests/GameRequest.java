package com.example.ratingsystem.adapters.inbound.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRequest {
    @NotBlank(message = "Game title is required")
    private String title;
    private String description;
}
