package com.example.ratingsystem.adapters.outbound.persistence.jpaAdapters;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.CommentRequest;
import com.example.ratingsystem.adapters.outbound.persistence.entities.CommentEntity;
import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.CommentMapper;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaCommentRepository;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaUserRepository;
import com.example.ratingsystem.application.ports.Comment.AddCommentPort;
import com.example.ratingsystem.application.ports.Comment.DeleteCommentPort;
import com.example.ratingsystem.application.ports.Comment.LoadCommentPort;
import com.example.ratingsystem.domain.models.Comment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CommentAdapter implements AddCommentPort, LoadCommentPort, DeleteCommentPort {
    private JpaCommentRepository jpaCommentRepository;
    private JpaUserRepository jpaUserRepository;
    private CommentMapper commentMapper;

    @Override
    public Comment add(CommentRequest commentRequest) {
        if (commentRequest == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }

        if (commentRequest.getTargetId() == null) {
            throw new IllegalArgumentException("Target user ID is required");
        }

        UserEntity target = jpaUserRepository.findById(commentRequest.getTargetId())
                .orElseThrow(() -> new IllegalArgumentException("Target user not found"));

        UserEntity author = null;
        if (commentRequest.getAuthorId() != null) {
            author = jpaUserRepository.findById(commentRequest.getAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("Author user not found"));
        }

        Optional<CommentEntity> existing =
                jpaCommentRepository.findByAuthor_IdAndTarget_Id(
                        commentRequest.getAuthorId(),
                        commentRequest.getTargetId()
                );

        CommentEntity entity;

        if (existing.isPresent()) {
            entity = existing.get();
            entity.setMessage(commentRequest.getMessage());
            entity.setUpdatedAt(LocalDateTime.now());
        } else {
            entity = commentMapper.requestToEntity(commentRequest, author, target);
        }

        CommentEntity saved = jpaCommentRepository.save(entity);

        return commentMapper.entityToDomain(saved);
    }

    @Override
    public Optional<Comment> load(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Comment id cannot be null");
        }

        CommentEntity commentEntity = jpaCommentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment entity was not found with id " + id));

        return Optional.of(commentMapper.entityToDomain(commentEntity));
    }

    @Override
    public Optional<Comment> loadByAuthorAndTargetId(Integer authorId, Integer targetId) {
        if (targetId == null) {
            throw new IllegalArgumentException("Target ID is required");
        }
        if (authorId == null) {
            return Optional.empty();
        }

        return jpaCommentRepository
                .findByAuthor_IdAndTarget_Id(authorId, targetId)
                .map(commentMapper::entityToDomain);
    }

    @Override
    public List<Comment> loadByTargetId(Integer targetId) {
        if (targetId == null) {
            throw new IllegalArgumentException("Target ID is required");
        }

        return jpaCommentRepository.findByTarget_Id(targetId).stream()
                .map(commentMapper::entityToDomain)
                .toList();
    }

    @Override
    public void deleteCommentById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Comment ID cannot be null");
        }

        if (!jpaCommentRepository.existsById(id)) {
            throw new IllegalArgumentException("Comment not found");
        }

        jpaCommentRepository.deleteById(id);
    }


}
