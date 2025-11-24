package com.example.ratingsystem.adapters.inbound.DTOs.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {
    private Integer id;
    private String title;
    private String description;
}
