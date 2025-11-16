package com.example.ratingsystem.adapters.outbound.persistence.repositories;

import com.example.ratingsystem.adapters.outbound.persistence.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentRepository extends JpaRepository<CommentEntity, Integer> {
}
