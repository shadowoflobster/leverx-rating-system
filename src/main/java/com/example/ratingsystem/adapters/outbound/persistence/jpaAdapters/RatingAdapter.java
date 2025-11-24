package com.example.ratingsystem.adapters.outbound.persistence.jpaAdapters;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.RatingRequest;
import com.example.ratingsystem.adapters.outbound.persistence.entities.RatingEntity;
import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.RatingMapper;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaRatingRepository;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaUserRepository;
import com.example.ratingsystem.application.ports.Rating.AddRatingPort;
import com.example.ratingsystem.application.ports.Rating.DeleteRatingPort;
import com.example.ratingsystem.application.ports.Rating.LoadRatingPort;
import com.example.ratingsystem.application.ports.User.LoadUserPort;
import com.example.ratingsystem.domain.models.Rating;
import com.example.ratingsystem.domain.models.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Component
@AllArgsConstructor
public class RatingAdapter implements AddRatingPort, LoadRatingPort, DeleteRatingPort {
    private final JpaUserRepository userRepository;
    private final RatingMapper ratingMapper;
    private final JpaRatingRepository ratingRepository;
    private final LoadUserPort loadUserPort;

    @Override
    @Transactional
    public Rating add(String authorEmail, Integer targetId, RatingRequest ratingRequest) {
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

        UserEntity target = userRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Target user not found"));

        UserEntity author = null;

        try {
            User user = loadUserPort.loadByEmail(authorEmail);
            if (user != null) {
                author = userRepository.findById(user.getId()).orElse(null);
            }
        } catch (Exception ignored) {
        }

        RatingEntity entity;

        if (author != null) {
            Optional<RatingEntity> existing = ratingRepository
                    .findByAuthor_IdAndTarget_Id(author.getId(), targetId);

            if (existing.isPresent()) {
                entity = existing.get();
                entity.setScore(score);
                entity.setUpdatedAt(LocalDateTime.now());
            } else {
                entity = new RatingEntity();
                entity.setAuthor(author);
                entity.setTarget(target);
                entity.setScore(score);
                entity.setCreatedAt(LocalDateTime.now());
            }
        } else {
            entity = new RatingEntity();
            entity.setAuthor(null);
            entity.setTarget(target);
            entity.setScore(score);
            entity.setCreatedAt(LocalDateTime.now());
        }

        RatingEntity saved = ratingRepository.save(entity);
        return ratingMapper.entityToDomain(saved);
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

    @Override
    public void deleteRatingById(Integer id) {
        ratingRepository.deleteById(id);
    }


}
