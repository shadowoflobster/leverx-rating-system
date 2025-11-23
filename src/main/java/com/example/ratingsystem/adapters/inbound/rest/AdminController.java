package com.example.ratingsystem.adapters.inbound.rest;

import com.example.ratingsystem.application.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/pending-users")
    public ResponseEntity<Map<String, Object>> getPendingUsers() {
        try {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", adminService.getPendingUsers()
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
}
