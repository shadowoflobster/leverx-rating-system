package com.example.ratingsystem.adapters.outbound.persistence.repositories;

import com.example.ratingsystem.adapters.outbound.persistence.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findByAuthor_IdAndTarget_Id(Integer authorId, Integer targetId);

    List<CommentEntity> findByTarget_Id(Integer targetId);

    List<CommentEntity> findByIsApprovedFalse();



}
