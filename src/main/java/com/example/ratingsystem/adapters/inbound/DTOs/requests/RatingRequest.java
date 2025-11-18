package com.example.ratingsystem.adapters.inbound.DTOs.requests;

import lombok.Value;

@Value
public class RatingRequest {
    short score;
    Integer authorId;
    Integer targetId;
}
