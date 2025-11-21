package com.example.ratingsystem.adapters.inbound.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameObjectResponse {
    private String title;
    private String description;
    private Integer userId;
    private String gameTitle;

}
