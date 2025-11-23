package com.example.ratingsystem.adapters.outbound.persistence.repositories;

import com.example.ratingsystem.adapters.outbound.persistence.entities.GameObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaGameObjectRepository extends JpaRepository<GameObjectEntity, Integer> {
    List<GameObjectEntity> findBySellerId(Integer sellerId);
    List<GameObjectEntity> findByGameId(Integer gameId);
    List<GameObjectEntity> findByNameContainingIgnoreCase(String name);
    List<GameObjectEntity> findByApprovedFalse();
}
