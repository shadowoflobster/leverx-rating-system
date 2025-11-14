package com.example.rating_system.application.ports.Comment;

import com.example.rating_system.domain.models.Comment;

import java.util.List;
import java.util.Optional;

public interface LoadCommentPort {
    Optional<Comment> load(Integer id);

    Optional<Comment> loadByAuthorAndTargetId(Integer authorId, Integer targetId);

    List<Comment> loadByTargetUserId(String targetId);
}
