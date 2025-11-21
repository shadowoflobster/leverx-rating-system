package com.example.ratingsystem.adapters.inbound.rest;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.CommentRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.CommentResponse;
import com.example.ratingsystem.application.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody CommentRequest request) {
        try {
            CommentResponse commentResponse = commentService.addComment(request);

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

    @GetMapping("/get/{id}")
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

    @GetMapping("/get_by_target_id/{targetId}")
    public ResponseEntity<?> getCommentByTargetId(@PathVariable("targetId") Integer targetId) {
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

    @GetMapping("/filter")
    public ResponseEntity<?> getCommentByAuthorAndTargetId(@RequestParam Integer authorId, @RequestParam Integer targetId) {
        try {
            CommentResponse commentResponse = commentService.loadCommentByAuthorAndTargetId(authorId, targetId);

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


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
