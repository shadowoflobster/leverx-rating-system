package com.example.ratingsystem.adapters.outbound.persistence.mappers;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.RatingRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.RatingResponse;
import com.example.ratingsystem.adapters.outbound.persistence.entities.RatingEntity;
import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import com.example.ratingsystem.domain.models.Rating;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RatingMapper {
    private UserMapper userMapper;


    public RatingResponse domainToResponse(Rating rating) {
        if (rating == null) return null;

        return new RatingResponse(
                rating.getId(),
                rating.getScore(),
                rating.getAuthor() != null ? rating.getAuthor().getId() : null,
                rating.getTargetSeller() != null ? rating.getTargetSeller().getId() : null,
                rating.getCreatedAt(),
                rating.getUpdatedAt()
        );
    }

    public RatingEntity requestToEntity(RatingRequest request, UserEntity author, UserEntity target) {
        if (request == null) {
            throw new IllegalArgumentException("RatingRequest cannot be null");
        }
        if (target == null) {
            throw new IllegalArgumentException("Target user cannot be null");
        }

        RatingEntity entity = new RatingEntity();
        entity.setScore(request.getScore());
        entity.setAuthor(author);
        entity.setTarget(target);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }

    public RatingEntity domainToEntity(Rating rating) {
        if (rating == null) {
            throw new IllegalArgumentException("Rating cannot be null");
        }

        RatingEntity entity = new RatingEntity();
        entity.setId(rating.getId());
        entity.setScore(rating.getScore());
        entity.setAuthor(rating.getAuthor() != null ? userMapper.domainToEntity(rating.getAuthor()) : null);
        entity.setTarget(rating.getTargetSeller() != null ? userMapper.domainToEntity(rating.getTargetSeller()) : null);
        entity.setCreatedAt(rating.getCreatedAt());
        entity.setUpdatedAt(rating.getUpdatedAt());

        return entity;
    }

    public Rating entityToDomain(RatingEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("RatingEntity cannot be null");
        }

        return Rating.builder()
                .id(entity.getId())
                .score(entity.getScore())
                .author(entity.getAuthor() != null ? userMapper.entityToDomain(entity.getAuthor()) : null)
                .targetSeller(entity.getTarget() != null ? userMapper.entityToDomain(entity.getTarget()) : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

}
