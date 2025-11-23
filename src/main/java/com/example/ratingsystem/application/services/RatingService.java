package com.example.ratingsystem.application.services;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.RatingRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.RatingResponse;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.RatingMapper;
import com.example.ratingsystem.application.ports.Rating.AddRatingPort;
import com.example.ratingsystem.domain.models.Rating;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RatingService {
    private AddRatingPort addRatingPort;
    public RatingMapper ratingMapper;


    public RatingResponse addRating(Integer authorId, Integer targetId, RatingRequest ratingRequest) {
        Rating ratingDomain = addRatingPort.add(authorId, targetId, ratingRequest);

        return ratingMapper.domainToResponse(ratingDomain);

    }
}
