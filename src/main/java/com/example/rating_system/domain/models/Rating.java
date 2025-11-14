package com.example.rating_system.domain.models;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Rating {
    private Integer id;
    private Short score;
    private User author;
    private User targetSeller;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Builder
    public Rating(Short score,
                  User author,
                  User targetSeller,
                  LocalDateTime createdAt,
                  LocalDateTime updatedAt
    ) {
        setScore(score);
        if (author == null) throw new IllegalArgumentException("Author required");
        if (targetSeller == null) throw new IllegalArgumentException("Target Seller required");
        this.author = author;
        this.targetSeller = targetSeller;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.updatedAt = updatedAt != null ? updatedAt : this.createdAt;
    }

    public void setScore(Short score) {
        if (score >= 1 && score <= 5) {
            this.score = score;
        } else throw new IllegalArgumentException("Score must be between 1 and 5");
        this.updatedAt = LocalDateTime.now();
    }

    public void setAuthor(User author) {
        if (author == null) throw new IllegalArgumentException("Author cannot be null");
        this.author = author;
        this.updatedAt = LocalDateTime.now();
    }

    public void setTargetSeller(User targetSeller) {
        if (targetSeller == null) throw new IllegalArgumentException("Target Seller cannot be null");
        this.targetSeller = targetSeller;
        this.updatedAt = LocalDateTime.now();
    }
}
