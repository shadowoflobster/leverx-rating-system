package com.example.ratingsystem.application.ports.Comment;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.CommentRequest;

public interface AddCommentPort {
    void add(CommentRequest commentRequest);
}
