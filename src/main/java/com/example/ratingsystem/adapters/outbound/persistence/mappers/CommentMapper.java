package com.example.ratingsystem.adapters.outbound.persistence.mappers;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.CommentRequest;
import com.example.ratingsystem.adapters.outbound.persistence.entities.CommentEntity;
import com.example.ratingsystem.adapters.outbound.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentEntity requestToEntity(CommentRequest request, UserEntity author, UserEntity target) {
        if (request == null) {
            return null;
        }

        CommentEntity entity = new CommentEntity();

        entity.setMessage(request.getMessage());
        entity.setAuthor(author);
        entity.setTarget(target);
        entity.setApproved(false);

        return entity;
    }
}
