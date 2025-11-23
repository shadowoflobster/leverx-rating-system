package com.example.ratingsystem.adapters.outbound.persistence.repositories;

import com.example.ratingsystem.adapters.outbound.persistence.entities.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaGameRepository extends JpaRepository<GameEntity, Integer> {
    Optional<GameEntity> findByTitle(String title);
}
