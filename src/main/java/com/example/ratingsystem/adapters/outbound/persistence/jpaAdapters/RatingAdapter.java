package com.example.ratingsystem.adapters.outbound.persistence.jpaAdapters;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.RatingRequest;
import com.example.ratingsystem.adapters.outbound.persistence.entities.RatingEntity;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.RatingMapper;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaRatingRepository;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaUserRepository;
import com.example.ratingsystem.application.ports.Rating.AddRatingPort;
import com.example.ratingsystem.application.ports.Rating.LoadRatingPort;
import com.example.ratingsystem.domain.models.Rating;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Component
@AllArgsConstructor
public class RatingAdapter implements AddRatingPort, LoadRatingPort {
    private JpaUserRepository userRepository;
    private RatingMapper ratingMapper;
    private JpaRatingRepository ratingRepository;

    @Override
    public Rating add(Integer authorId, Integer targetId, RatingRequest ratingRequest) {
        if (targetId == null) {
            throw new IllegalArgumentException("Target id cannot be null");
        }
        if (ratingRequest == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        short score = ratingRequest.getScore();
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }

        if (authorId != null) {
            Optional<RatingEntity> existing =
                    ratingRepository.findByAuthor_IdAndTarget_Id(authorId, targetId);

            if (existing.isPresent()) {
                RatingEntity entity = existing.get();
                entity.setScore(score);
                entity.setUpdatedAt(LocalDateTime.now());
                return ratingMapper.entityToDomain(ratingRepository.save(entity));
            }

            RatingEntity entity = new RatingEntity();
            entity.setScore(score);
            entity.setAuthor(userRepository.getReferenceById(authorId));
            entity.setTarget(userRepository.getReferenceById(targetId));
            entity.setCreatedAt(LocalDateTime.now());

            return ratingMapper.entityToDomain(ratingRepository.save(entity));
        }
        RatingEntity anonymous = new RatingEntity();
        anonymous.setScore(score);
        anonymous.setTarget(userRepository.getReferenceById(targetId));
        anonymous.setCreatedAt(LocalDateTime.now());

        return ratingMapper.entityToDomain(ratingRepository.save(anonymous));
    }

    @Override
    public Rating loadById(Integer id) {
        RatingEntity ratingEntity = ratingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rating didn't found by ID: " + id));

        return ratingMapper.entityToDomain(ratingEntity);
    }

    @Override
    public Rating loadByAuthorAndTargetId(Integer authorId, Integer targetId) {
        RatingEntity ratingEntity = ratingRepository.findByAuthor_IdAndTarget_Id(authorId, targetId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Rating didn't found for user with id:" + targetId + " from user with id: " + targetId));
        return ratingMapper.entityToDomain(ratingEntity);
    }

    @Override
    public Short loadAverageRatingByTargetId(Integer targetId) {
        List<Rating> domains = loadByTargetUserId(targetId);

        OptionalDouble avg = domains.stream()
                .mapToInt(Rating::getScore)
                .average();

        return (short) Math.round(avg.orElse(0));
    }

    ;

    @Override
    public List<Rating> loadByTargetUserId(Integer targetId) {
        List<RatingEntity> entities = ratingRepository.findByTargetId(targetId);

        return entities.stream()
                .map(ratingMapper::entityToDomain)
                .toList();
    }


}
