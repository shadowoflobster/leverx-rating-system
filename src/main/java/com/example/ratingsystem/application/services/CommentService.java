package com.example.ratingsystem.application.services;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.CommentRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.CommentResponse;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.CommentMapper;
import com.example.ratingsystem.application.ports.Comment.AddCommentPort;
import com.example.ratingsystem.domain.models.Comment;
import com.example.ratingsystem.domain.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {
    private AddCommentPort addCommentPort;
    private final CommentMapper commentMapper;

    public CommentResponse addComment(CommentRequest commentRequest){
        Comment commentDomain = addCommentPort.add(commentRequest);

        return commentMapper.domainToResponse(commentDomain);

    }

}
