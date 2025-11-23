package com.example.ratingsystem.application.services;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.CommentRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.CommentResponse;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.CommentMapper;
import com.example.ratingsystem.application.ports.Comment.AddCommentPort;
import com.example.ratingsystem.application.ports.Comment.DeleteCommentPort;
import com.example.ratingsystem.application.ports.Comment.LoadCommentPort;
import com.example.ratingsystem.application.ports.Comment.UpdateCommentPort;
import com.example.ratingsystem.domain.models.Comment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private AddCommentPort addCommentPort;
    private LoadCommentPort loadCommentPort;
    private DeleteCommentPort deleteCommentPort;
    private final CommentMapper commentMapper;
    private UpdateCommentPort updateCommentPort;

    public CommentResponse addComment(Integer targetId, CommentRequest commentRequest, String email) {
        Comment commentDomain = addCommentPort.add(targetId, email, commentRequest);

        return commentMapper.domainToResponse(commentDomain);

    }

    public CommentResponse loadCommentById(Integer commentId) {
        Comment commentModel = loadCommentPort.load(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        return commentMapper.domainToResponse(commentModel);
    }

    public CommentResponse loadCommentByIdForUser(Integer userId, Integer commentId) {
        if (userId == null || commentId == null) {
            throw new IllegalArgumentException("User ID and Comment ID are required");
        }

        Comment comment = loadCommentPort.load(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (!comment.getTargetSeller().getId().equals(userId)) {
            throw new IllegalArgumentException("Comment does not belong to this user");
        }

        return commentMapper.domainToResponse(comment);
    }

    public List<CommentResponse> loadCommentByAuthorAndTargetId(Integer authorId, Integer targetId) {
        List<Comment> commentDomain = loadCommentPort.loadByAuthorAndTargetId(authorId, targetId);

        if (commentDomain == null || commentDomain.isEmpty()) {
            return List.of();
        }

        return commentDomain.stream()
                .map(commentMapper::domainToResponse)
                .toList();
    }

    public List<CommentResponse> loadCommentByTargetId(Integer targetId) {
        List<Comment> commentModels = loadCommentPort.loadByTargetId(targetId);

        return commentModels.stream()
                .map(commentMapper::domainToResponse)
                .toList();
    }


    public void deleteById(Integer id) {
        deleteCommentPort.deleteCommentById(id);
    }

    public CommentResponse updateComment(Integer id, CommentRequest request) {
        Comment comment = updateCommentPort.updateComment(id, request);


        return commentMapper.domainToResponse(comment);
    }


}
