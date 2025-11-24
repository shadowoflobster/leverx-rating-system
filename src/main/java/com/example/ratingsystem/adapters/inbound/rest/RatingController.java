package com.example.ratingsystem.adapters.inbound.rest;

import com.example.ratingsystem.adapters.inbound.DTOs.requests.RatingRequest;
import com.example.ratingsystem.adapters.inbound.DTOs.responses.RatingResponse;
import com.example.ratingsystem.application.services.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;

    @PostMapping("/{targetId}")
    public ResponseEntity<?> addRating(
            @PathVariable("targetId") Integer targetId,
            @RequestBody RatingRequest ratingRequest,
            Authentication authentication
    ) {
        String authorEmail = (authentication != null && authentication.isAuthenticated())
                ? authentication.getName()
                : null;
        try {
            RatingResponse response = ratingService.addRating(authorEmail, targetId, ratingRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "success", true,
                    "message", "Rating added successfully",
                    "data", response
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }
}
