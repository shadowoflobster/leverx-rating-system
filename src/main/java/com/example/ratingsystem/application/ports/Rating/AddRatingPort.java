package com.example.ratingsystem.application.ports.Rating;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.RatingRequest;
import com.example.ratingsystem.domain.models.Rating;

public interface AddRatingPort {
    Rating add(Integer authorId, Integer targetId, RatingRequest ratingRequest);

}
