package com.example.ratingsystem.adapters.inbound.rest;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.GameObjectRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.GameObjectResponse;
import com.example.ratingsystem.application.services.GameObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/objects")
@RequiredArgsConstructor
public class GameObjectController {
    private final GameObjectService gameObjectService;

    @PostMapping("")
    public ResponseEntity<?> addObject(
            @RequestBody GameObjectRequest request,
            Authentication authentication
    ) {
        try {
            if (authentication == null) {
                throw new IllegalArgumentException("You should be logged in to continue");
            }
            String email = authentication.getName();
            GameObjectResponse gameObject = gameObjectService.addGameObject(email, request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                            "success", true,
                            "message", "Game object added successfully!",
                            "data", gameObject
                    ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", ex.getMessage()
                    ));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Unexpected error: " + ex.getMessage()
                    ));
        }
    }
}