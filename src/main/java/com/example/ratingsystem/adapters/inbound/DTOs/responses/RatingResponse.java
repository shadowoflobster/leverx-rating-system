package com.example.ratingsystem.adapters.inbound.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {
    private Integer id;
    private short score;
    private Integer authorId;
    private Integer targetId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
