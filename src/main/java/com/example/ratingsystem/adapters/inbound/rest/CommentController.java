package com.example.ratingsystem.adapters.inbound.rest;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.CommentRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.CommentResponse;
import com.example.ratingsystem.application.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addComment(@PathVariable("id") Integer id,
                                        @RequestBody CommentRequest request,
                                        Authentication authentication
    ) {
        System.out.println("Authentication object: " + authentication);
        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal() == null) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "success", false,
                            "message", "You must be logged in to comment"
                    ));
        }
        String authorEmail = authentication.getName();
        try {
            CommentResponse commentResponse = commentService.addComment(id, request, authorEmail);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                            "success", true,
                            "message", "Comment added successfully!",
                            "data", commentResponse
                    ));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", ex.getMessage()
                    ));
        }
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable("id") Integer id) {
        try {
            CommentResponse commentResponse = commentService.loadCommentById(id);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                            "success", true,
                            "data", commentResponse

                    ));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", ex.getMessage()
                    ));
        }

    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<?> getCommentByTargetId(@PathVariable("id") Integer targetId) {
        try {
            List<CommentResponse> commentResponses = commentService.loadCommentByTargetId(targetId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                            "success", true,
                            "data", commentResponses

                    ));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", ex.getMessage()
                    ));
        }

    }

    @GetMapping("/{userId}/comments/{commentId}")
    public ResponseEntity<?> getComment(
            @PathVariable Integer userId,
            @PathVariable Integer commentId) {

        CommentResponse commentResponse = commentService.loadCommentByIdForUser(userId, commentId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", commentResponse
        ));
    }

    @GetMapping("/comments/filter")
    public ResponseEntity<?> getCommentByAuthorAndTargetId(@RequestParam Integer authorId,
                                                           @RequestParam Integer targetId) {
        try {
            List<CommentResponse> commentResponses = commentService.loadCommentByAuthorAndTargetId(authorId, targetId);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                            "success", true,
                            "data", commentResponses

                    ));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", ex.getMessage()
                    ));
        }


    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Integer id, @RequestBody CommentRequest request) {

        try {
            CommentResponse response = commentService.updateComment(id, request);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                            "success", true,
                            "data", response

                    ));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", ex.getMessage()
                    ));
        }

    }

}
