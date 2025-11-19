package com.example.ratingsystem.application.services;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.CommentRequest;
import com.example.ratingsystem.application.ports.Comment.AddCommentPort;
import com.example.ratingsystem.domain.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {
    private AddCommentPort addCommentPort;

    public void addComment(CommentRequest commentRequest){
        addCommentPort.add(commentRequest);
    }

}
