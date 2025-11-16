package com.example.ratingsystem.adapters.outbound.persistence.repositories;

import com.example.ratingsystem.adapters.outbound.persistence.entities.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRatingRepository extends JpaRepository<RatingEntity, Integer> {
}
