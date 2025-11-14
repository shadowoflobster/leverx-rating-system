package com.example.rating_system.application.ports.Comment;

import com.example.rating_system.domain.models.Comment;

public interface AddCommentPort {
    void add(Comment comment);
}
