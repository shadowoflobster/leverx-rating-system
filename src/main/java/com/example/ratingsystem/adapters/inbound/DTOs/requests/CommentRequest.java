package com.example.ratingsystem.adapters.inbound.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class CommentRequest {
    @NotBlank(message = "Message text is required")
    String message;
    Integer authorId;

    Integer targetId;

}
