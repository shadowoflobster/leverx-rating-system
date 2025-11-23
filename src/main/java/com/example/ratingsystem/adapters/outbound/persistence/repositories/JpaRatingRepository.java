package com.example.ratingsystem.adapters.outbound.persistence.repositories;

import com.example.ratingsystem.adapters.outbound.persistence.entities.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaRatingRepository extends JpaRepository<RatingEntity, Integer> {
    Optional<RatingEntity> findByAuthor_IdAndTarget_Id(Integer authorId, Integer targetId);

    List<RatingEntity> findByTargetId(Integer targetId);
}
