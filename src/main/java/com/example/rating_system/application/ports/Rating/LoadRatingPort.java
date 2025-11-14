package com.example.rating_system.application.ports.Rating;

import com.example.rating_system.domain.models.Rating;

import java.util.List;
import java.util.Optional;

public interface LoadRatingPort {
    Optional<Rating> load(Integer id);

    Optional<Rating> loadByAuthorAndTargetId(Integer authorId, Integer targetId);

    Optional<Short> loadAverageRatingByTargetId(Integer targetId);

    List<Rating> loadByTargetUserId(String targetId);
}
