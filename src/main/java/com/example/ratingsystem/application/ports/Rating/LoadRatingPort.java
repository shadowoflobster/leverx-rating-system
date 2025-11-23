package com.example.ratingsystem.application.ports.Rating;

import com.example.ratingsystem.domain.models.Rating;

import java.util.List;

public interface LoadRatingPort {
    Rating loadById(Integer id);

    Rating loadByAuthorAndTargetId(Integer authorId, Integer targetId);

    Short loadAverageRatingByTargetId(Integer targetId);

    List<Rating> loadByTargetUserId(Integer targetId);
}
