package com.example.ratingsystem.application.ports.Comment;

import com.example.ratingsystem.domain.models.Comment;

public interface AddCommentPort {
    void add(Comment comment);
}
