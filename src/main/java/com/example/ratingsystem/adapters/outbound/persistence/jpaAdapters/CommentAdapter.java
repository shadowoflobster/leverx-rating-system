package com.example.ratingsystem.adapters.outbound.persistence.jpaAdapters;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.CommentRequest;
import com.example.ratingsystem.adapters.outbound.persistence.entities.CommentEntity;
import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.CommentMapper;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaCommentRepository;
import com.example.ratingsystem.adapters.outbound.persistence.repositories.JpaUserRepository;
import com.example.ratingsystem.application.ports.Comment.AddCommentPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentAdapter implements AddCommentPort {
    private JpaCommentRepository jpaCommentRepository;
    private JpaUserRepository jpaUserRepository;
    private CommentMapper commentMapper;

    @Override
    public void add(CommentRequest commentRequest) {
        if (commentRequest == null) {
            throw new IllegalArgumentException("Comment cannot be null");
        }

        UserEntity target = jpaUserRepository.findById(commentRequest.getTargetId())
                .orElseThrow(() -> new IllegalArgumentException("Target user not found"));

        UserEntity author = null;
        if (commentRequest.getAuthorId() != null) {
            author = jpaUserRepository.findById(commentRequest.getAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("Author user not found"));
        }

        CommentEntity entity = commentMapper.requestToEntity(commentRequest, author, target);

        jpaCommentRepository.save(entity);
    }

}
