package com.openclassrooms.back.dto;

import com.openclassrooms.back.models.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String username;
    private String topicTitle;
    private LocalDateTime createdAt;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUser().getName();
        this.topicTitle = post.getTopic().getTitle();
        this.createdAt = post.getCreatedAt();
    }
}
