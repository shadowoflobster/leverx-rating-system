package com.example.ratingsystem.adapters.inbound.DTOs.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequest {
    private short score;
    private Integer authorId;
    private Integer targetId;
}