package com.example.ratingsystem.application.services;

import com.example.ratingsystem.adapters.inbound.DTOs.responses.CommentResponse;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.GameObjectResponse;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.UserResponse;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.CommentMapper;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.GameObjectMapper;
import com.example.ratingsystem.adapters.outbound.persistence.mappers.UserMapper;
import com.example.ratingsystem.application.ports.Comment.LoadCommentPort;
import com.example.ratingsystem.application.ports.Comment.UpdateCommentPort;
import com.example.ratingsystem.application.ports.GameObject.LoadGameObjectPort;
import com.example.ratingsystem.application.ports.GameObject.UpdateGameObjectPort;
import com.example.ratingsystem.application.ports.User.DeleteUserPort;
import com.example.ratingsystem.application.ports.User.LoadUserPort;
import com.example.ratingsystem.application.ports.User.SaveUserPort;
import com.example.ratingsystem.domain.models.Comment;
import com.example.ratingsystem.domain.models.GameObject;
import com.example.ratingsystem.domain.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {
    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;
    private final LoadGameObjectPort loadGameObjectPort;
    private final LoadCommentPort loadCommentPort;
    private final UpdateCommentPort updateCommentPort;
    private final UpdateGameObjectPort updateGameObjectPort;
    private final GameObjectMapper gameObjectMapper;
    private final DeleteUserPort deleteUserPort;
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    private final EmailService emailService;

    public List<UserResponse> getPendingUsers() {
        List<User> users = loadUserPort.loadPendingUsers();

        return users.stream()
                .map(userMapper::domainToResponse)
                .toList();
    }

    public List<GameObjectResponse> getPendingObjects() {
        List<GameObject> objects = loadGameObjectPort.loadPendingObjects();

        return objects.stream()
                .map(gameObjectMapper::domainToResponse)
                .toList();
    }

    public List<CommentResponse> getPendingComments() {
        List<Comment> comments = loadCommentPort.loadPendingComments();
        return comments.stream()
                .map(commentMapper::domainToResponse)
                .toList();
    }

    public void approveUser(Integer id) {
        User user = loadUserPort.loadById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        emailService.sendVerificationEmail(user.getEmail());
        if (user.isApproved()) {
            throw new IllegalArgumentException("User is already approved, Resending code...");
        }

        user.setApproved(true);

        saveUserPort.save(user);
    }

    public void approveComment(Integer id) {
        Comment comment = loadCommentPort.load(id)
                .orElseThrow(() -> new IllegalArgumentException("No comment found"));
        if (comment.isApproved()) {
            throw new IllegalArgumentException("Comment is already approved");
        }
        comment.setApproved(true);

        updateCommentPort.update(comment);
    }

    public void approveObject(Integer id) {
        GameObject object = loadGameObjectPort.loadById(id)
                .orElseThrow(() -> new IllegalArgumentException("Object not found"));

        if (object.isApproved()) {
            throw new IllegalArgumentException("Game Object is already approved");
        }

        object.setApproved(true);

        updateGameObjectPort.update(object);
    }

    public void rejectUser(Integer id) {
        User user = loadUserPort.loadById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (user.isApproved()) {
            throw new IllegalArgumentException("User is already approved");
        }

        deleteUserPort.deleteById(id);
    }
}
