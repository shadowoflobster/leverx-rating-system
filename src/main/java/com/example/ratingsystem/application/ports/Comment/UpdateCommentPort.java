package com.example.ratingsystem.application.ports.Comment;

import com.example.ratingsystem.domain.models.Comment;

public interface UpdateCommentPort {
    void updateComment(Comment comment);
}

