package com.example.ratingsystem.adapters.inbound.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Integer id;
    private String message;
    private UserResponse author;
    private UserResponse target;
    private boolean approved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
