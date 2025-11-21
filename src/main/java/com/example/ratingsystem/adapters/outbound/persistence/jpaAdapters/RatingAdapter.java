package com.example.ratingsystem.adapters.outbound.persistence.jpaAdapters;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.RatingRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.RatingResponse;
import com.example.ratingsystem.adapters.outbound.persistence.entities.RatingEntity;
import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.RatingMapper;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.UserMapper;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaRatingRepository;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaUserRepository;
import com.example.ratingsystem.application.ports.Rating.AddRatingPort;
import com.example.ratingsystem.application.ports.Rating.LoadRatingPort;
import com.example.ratingsystem.domain.models.Rating;

import java.time.LocalDateTime;
import java.util.Optional;

public class RatingAdapter implements AddRatingPort {
    private JpaUserRepository userRepository;
    private LoadRatingPort loadRatingPort;
    private UserMapper userMapper;
    private RatingMapper ratingMapper;
    private JpaRatingRepository ratingRepository;

    @Override
    public Rating add(RatingRequest ratingRequest) {
        if (ratingRequest == null) {
            throw new IllegalArgumentException("Rating cannot be null");
        }

        if (ratingRequest.getTargetId() == null) {
            throw new IllegalArgumentException("Target user ID is required");
        }

        UserEntity target = userRepository.findById(ratingRequest.getTargetId())
                .orElseThrow(() -> new IllegalArgumentException("Target seller not found"));

        UserEntity authorEntity = null;
        if (ratingRequest.getAuthorId() != null) {
            authorEntity = userRepository.findById(ratingRequest.getAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("Author user not found"));
        }

        RatingEntity ratingEntity;

        if (ratingRequest.getAuthorId() != null) {
            Optional<Rating> existingRating = loadRatingPort
                    .loadByAuthorAndTargetId(ratingRequest.getAuthorId(), ratingRequest.getTargetId());

            if (existingRating.isPresent()) {
                ratingEntity = ratingMapper.domainToEntity(existingRating.get());
                ratingEntity.setScore(ratingRequest.getScore());
                ratingEntity.setUpdatedAt(LocalDateTime.now());
            } else {
                ratingEntity = ratingMapper.requestToEntity(ratingRequest, authorEntity, target);
            }
        } else {
            ratingEntity = ratingMapper.requestToEntity(ratingRequest, null, target);
        }

        RatingEntity savedEntity = ratingRepository.save(ratingEntity);
        return ratingMapper.entityToDomain(savedEntity);
    }
}
