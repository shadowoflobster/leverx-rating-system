package com.example.ratingsystem.adapters.inbound.rest;

import com.example.ratingsystem.application.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {
    private final SellerService sellerService;

    @GetMapping("/{id}/average-rating")
    public ResponseEntity<?> getAverageRating(@PathVariable Integer id) {
        try {
            Short avg = sellerService.getAverageRating(id);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "averageRating", avg
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    @GetMapping("/top/{count}")
    public ResponseEntity<?> getTopSellers(@PathVariable Integer count) {
        try {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", sellerService.getTopSellers(count)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }
}
