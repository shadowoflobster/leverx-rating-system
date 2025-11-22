package com.example.ratingsystem.application.ports.Comment;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.CommentRequest;
import com.example.ratingsystem.domain.models.Comment;

public interface AddCommentPort {
    Comment add(Integer targetId, String authorEmail, CommentRequest request);
}
