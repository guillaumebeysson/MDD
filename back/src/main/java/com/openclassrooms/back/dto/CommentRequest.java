package com.openclassrooms.back.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {
    @NotBlank
    private String content;

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "postId is required")
    private Long postId;
}
