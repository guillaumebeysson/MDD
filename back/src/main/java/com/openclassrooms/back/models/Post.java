package com.openclassrooms.back.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "POSTS")
@Data
public class Post {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        @Lob
        @Column(nullable = false, columnDefinition = "TEXT")
        private String content;

        @CreationTimestamp
        @Column(nullable = false)
        private LocalDateTime createdAt;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name = "topic_id", nullable = false)
        private Topic topic;

        @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
        private List<Comment> comments;
}
