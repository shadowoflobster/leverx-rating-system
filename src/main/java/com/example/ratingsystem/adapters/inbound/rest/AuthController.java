package com.example.ratingsystem.adapters.inbound.rest;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.LoginRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.requests.UserRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.LoginResponse;
import com.example.ratingsystem.application.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User Registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok(new LoginResponse(token));
    }

}
