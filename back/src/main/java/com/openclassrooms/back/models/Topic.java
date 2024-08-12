package com.openclassrooms.back.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TOPICS")
@Data
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;
}
