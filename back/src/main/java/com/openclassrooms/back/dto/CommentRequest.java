package com.openclassrooms.back.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private String content;
    private Long userId;
    private Long postId;
}
