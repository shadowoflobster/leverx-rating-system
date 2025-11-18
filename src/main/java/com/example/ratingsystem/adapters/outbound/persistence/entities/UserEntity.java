package com.example.ratingsystem.adapters.outbound.persistence.entities;

import com.example.ratingsystem.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@Table(name = "users", schema = "leverx_ratings")
@Getter
@Setter
public class UserEntity {
    @Id
    private Integer id;

    @Column(name = "first_name")
    private String fistName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<GameObjectEntity> gameObjects;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<CommentEntity> givenComments;

    @OneToMany(mappedBy = "target", fetch = FetchType.LAZY)
    private List<CommentEntity> takenComments;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<RatingEntity> givenRating;

    @OneToMany(mappedBy = "target", fetch = FetchType.LAZY)
    private List<RatingEntity> takenRating;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;


}
