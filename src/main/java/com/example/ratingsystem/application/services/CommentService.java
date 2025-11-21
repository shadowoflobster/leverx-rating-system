package com.example.ratingsystem.application.services;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.CommentRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.CommentResponse;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.CommentMapper;
import com.example.ratingsystem.application.ports.Comment.AddCommentPort;
import com.example.ratingsystem.application.ports.Comment.DeleteCommentPort;
import com.example.ratingsystem.application.ports.Comment.LoadCommentPort;
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

    public CommentResponse addComment(CommentRequest commentRequest) {
        Comment commentDomain = addCommentPort.add(commentRequest);

        return commentMapper.domainToResponse(commentDomain);

    }

    public CommentResponse loadCommentById(Integer commentId) {
        Comment commentModel = loadCommentPort.load(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        return commentMapper.domainToResponse(commentModel);
    }

    public CommentResponse loadCommentByAuthorAndTargetId(Integer authorId, Integer targetId) {
        Comment commentDomain = loadCommentPort.loadByAuthorAndTargetId(authorId, targetId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        return commentMapper.domainToResponse(commentDomain);
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


}
