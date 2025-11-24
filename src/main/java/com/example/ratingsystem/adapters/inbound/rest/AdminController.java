package com.example.ratingsystem.adapters.inbound.rest;

import com.example.ratingsystem.adapters.inbound.DTOs.responses.UserResponse;
import com.example.ratingsystem.application.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @PreAuthorize("hasAuthority('Administrator')")
    @GetMapping("/pending-users")
    public ResponseEntity<Map<String, Object>> getPendingUsers() {
        try {
            List<UserResponse> users = adminService.getPendingUsers();

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", users
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }

    @GetMapping("/pending-objects")
    public ResponseEntity<Map<String, Object>> getPendingObjects() {
        try {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", adminService.getPendingObjects()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }

    @PostMapping("/approve-object/{objectId}")
    public ResponseEntity<Map<String, Object>> approveObject(@PathVariable Integer objectId) {
        try {
            adminService.approveObject(objectId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Object approved successfully!"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }


    @PostMapping("/approve-user/{userId}")
    public ResponseEntity<Map<String, Object>> approveUser(@PathVariable Integer userId) {
        try {
            adminService.approveUser(userId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "User approved successfully! Verification email sent."
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }

    @GetMapping("/pending-comment")
    public ResponseEntity<Map<String, Object>> getPendingComments() {
        try {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", adminService.getPendingComments()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }

    @PostMapping("/approve-comment/{commentId}")
    public ResponseEntity<Map<String, Object>> approveComment(@PathVariable Integer commentId) {
        try {
            adminService.approveComment(commentId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Comment approved successfully!"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }
}
