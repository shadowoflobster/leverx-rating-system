package com.example.ratingsystem.adapters.outbound.persistence.mappers;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.CommentRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.CommentResponse;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.UserResponse;
import com.example.ratingsystem.adapters.outbound.persistence.entities.CommentEntity;
import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaUserRepository;
import com.example.ratingsystem.domain.models.Comment;
import com.example.ratingsystem.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final JpaUserRepository userRepository;

    public CommentEntity requestToEntity(CommentRequest request, UserEntity author, UserEntity target) {
        if (request == null) {
            throw new IllegalArgumentException("CommentRequest cannot be null");
        }

        if (target == null) {
            throw new IllegalArgumentException("Target user cannot be null");
        }

        CommentEntity entity = new CommentEntity();

        entity.setMessage(request.getMessage());
        entity.setAuthor(author);
        entity.setTarget(target);
        entity.setApproved(false);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }

    public Comment entityToDomain(CommentEntity entity) {
        if (entity == null) {
            return null;
        }

        User authorDomain = null;
        if (entity.getAuthor() != null) {
            authorDomain = User.builder()
                    .id(entity.getAuthor().getId())
                    .firstName(entity.getAuthor().getFirstName())
                    .lastName(entity.getAuthor().getLastName())
                    .email(entity.getAuthor().getEmail())
                    .role(entity.getAuthor().getRole())
                    .build();
        }

        User targetDomain = User.builder()
                .id(entity.getTarget().getId())
                .firstName(entity.getTarget().getFirstName())
                .lastName(entity.getTarget().getLastName())
                .email(entity.getTarget().getEmail())
                .role(entity.getTarget().getRole())
                .build();

        return Comment.builder()
                .id(entity.getId())
                .message(entity.getMessage())
                .author(authorDomain)
                .targetSeller(targetDomain)
                .approved(entity.isApproved())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public CommentResponse domainToResponse(Comment comment) {
        if (comment == null) return null;

        UserResponse authorResponse = null;
        if (comment.getAuthor() != null) {
            authorResponse = new UserResponse(
                    comment.getAuthor().getId(),
                    comment.getAuthor().getFirstName(),
                    comment.getAuthor().getLastName(),
                    comment.getAuthor().getEmail(),
                    comment.getAuthor().getCreatedAt(),
                    comment.getAuthor().getRole().name(),
                    null,
                    null
            );
        }

        UserResponse targetResponse = new UserResponse(
                comment.getTargetSeller().getId(),
                comment.getTargetSeller().getFirstName(),
                comment.getTargetSeller().getLastName(),
                comment.getTargetSeller().getEmail(),
                comment.getTargetSeller().getCreatedAt(),
                comment.getTargetSeller().getRole().name(),
                null,
                null
        );

        return new CommentResponse(
                comment.getId(),
                comment.getMessage(),
                authorResponse,
                targetResponse,
                comment.isApproved(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    public CommentEntity domainToEntity(Comment domain) {
        if (domain == null) {
            throw new IllegalArgumentException("Comment domain cannot be null");
        }

        CommentEntity entity = new CommentEntity();

        entity.setId(domain.getId());
        entity.setMessage(domain.getMessage());
        entity.setApproved(domain.isApproved());
        entity.setCreatedAt(domain.getCreatedAt() != null ? domain.getCreatedAt() : LocalDateTime.now());
        entity.setUpdatedAt(domain.getUpdatedAt() != null ? domain.getUpdatedAt() : LocalDateTime.now());

        if (domain.getAuthor() != null && domain.getAuthor().getId() != null) {
            entity.setAuthor(userRepository.getReferenceById(domain.getAuthor().getId()));
        } else {
            entity.setAuthor(null);
        }

        if (domain.getTargetSeller() != null && domain.getTargetSeller().getId() != null) {
            entity.setTarget(userRepository.getReferenceById(domain.getTargetSeller().getId()));
        } else {
            throw new IllegalArgumentException("Target user required");
        }

        return entity;
    }
}


        