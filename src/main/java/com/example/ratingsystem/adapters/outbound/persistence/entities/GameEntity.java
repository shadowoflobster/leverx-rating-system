package com.example.ratingsystem.adapters.outbound.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;


}
