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
import com.example.ratingsystem.application.ports.Comment.UpdateCommentPort;
import com.example.ratingsystem.domain.models.Comment;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CommentAdapter implements AddCommentPort, LoadCommentPort, DeleteCommentPort, UpdateCommentPort {
    private JpaCommentRepository jpaCommentRepository;
    private JpaUserRepository jpaUserRepository;
    private CommentMapper commentMapper;

    @Override
    public Comment add(Integer targetId, String authorEmail, CommentRequest request) {
        if (targetId == null) {
            throw new IllegalArgumentException("Target user ID is required");
        }

        if (request == null || request.getMessage() == null || request.getMessage().isBlank()) {
            throw new IllegalArgumentException("Comment content is required");
        }

        UserEntity target = jpaUserRepository.findById(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Target user not found"));

        UserEntity author = null;

        if (authorEmail != null) {
            author = jpaUserRepository.findByEmail(authorEmail)
                    .orElseThrow(() -> new IllegalArgumentException("Author user not found"));
        }

        CommentEntity entity = commentMapper.requestToEntity(request, author, target);

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
    public List<Comment> loadByAuthorAndTargetId(Integer authorId, Integer targetId) {
        if (targetId == null) {
            throw new IllegalArgumentException("Target ID is required");
        }

        if (authorId == null) {
            return List.of(); // empty list
        }

        return jpaCommentRepository
                .findByAuthor_IdAndTarget_Id(authorId, targetId)
                .stream()
                .map(commentMapper::entityToDomain)
                .toList();
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
    public List<Comment> loadPendingComments() {
        return jpaCommentRepository.findByIsApprovedFalse().stream()
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

    @Override
    @Transactional
    public Comment updateComment(Integer id, CommentRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (request == null) {
            throw new IllegalArgumentException("request cannot be null");
        }

        CommentEntity entity = jpaCommentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        entity.setMessage(request.getMessage());

        CommentEntity saved = jpaCommentRepository.save(entity);

        return commentMapper.entityToDomain(saved);
    }

    @Override
    @Transactional
    public Comment update(Comment comment) {
        CommentEntity entity = commentMapper.domainToEntity(comment);
        CommentEntity saved = jpaCommentRepository.save(entity);
        return commentMapper.entityToDomain(saved);

    }


}
