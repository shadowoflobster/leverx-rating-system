package com.example.ratingsystem.application.services;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.RatingRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.RatingResponse;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.RatingMapper;
import com.example.ratingsystem.application.ports.Rating.AddRatingPort;
import com.example.ratingsystem.application.ports.Rating.DeleteRatingPort;
import com.example.ratingsystem.application.ports.Rating.LoadRatingPort;
import com.example.ratingsystem.domain.models.Rating;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RatingService {
    private final AddRatingPort addRatingPort;
    private final LoadRatingPort loadRatingPort;
    private final DeleteRatingPort deleteRatingPort;
    private final RatingMapper ratingMapper;


    public RatingResponse addRating(String authorEmail, Integer targetId, RatingRequest ratingRequest) {
        Rating ratingDomain = addRatingPort.add(authorEmail, targetId, ratingRequest);
        return ratingMapper.domainToResponse(ratingDomain);
    }

    public RatingResponse getRatingById(Integer id) {
        Rating rating = loadRatingPort.loadById(id);
        return ratingMapper.domainToResponse(rating);
    }

    public RatingResponse getRatingByAuthorAndTarget(Integer authorId, Integer targetId) {
        Rating rating = loadRatingPort.loadByAuthorAndTargetId(authorId, targetId);
        return ratingMapper.domainToResponse(rating);
    }

    public void deleteRating(Integer id) {
        deleteRatingPort.deleteRatingById(id);
    }


}
