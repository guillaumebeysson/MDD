package com.openclassrooms.back.dto;

import lombok.Data;

@Data
public class PostRequest {
    private String title;
    private String content;
    private Long userId;
    private Long topicId;
}
