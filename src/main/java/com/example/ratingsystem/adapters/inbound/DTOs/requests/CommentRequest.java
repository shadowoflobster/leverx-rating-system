package com.example.ratingsystem.adapters.inbound.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    @NotBlank(message = "Message text is required")
    private String message;
    private Integer authorId;

    private Integer targetId;

}
