package com.example.ratingsystem.adapters.outbound.persistence.repositories;

import com.example.ratingsystem.adapters.outbound.persistence.entities.GameObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGameObjectRepository extends JpaRepository<GameObjectEntity, Integer> {
}
