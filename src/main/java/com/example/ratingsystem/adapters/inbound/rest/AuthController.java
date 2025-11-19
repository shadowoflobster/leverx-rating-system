package com.example.ratingsystem.adapters.inbound.rest;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.UserRequest;
import com.example.ratingsystem.application.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User Registered successfully");
    }
}
