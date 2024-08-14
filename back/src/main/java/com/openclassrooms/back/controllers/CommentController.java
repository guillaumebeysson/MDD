package com.openclassrooms.back.controllers;

import com.openclassrooms.back.dto.CommentRequest;
import com.openclassrooms.back.dto.CommentResponse;
import com.openclassrooms.back.services.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Comments", description = "Endpoints for managing comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public CommentResponse getCommentById(@PathVariable Long id) {
        return new CommentResponse(commentService.getCommentById(id));
    }

    @GetMapping("/post/{postId}")
    public List<CommentResponse> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId).stream().map(CommentResponse::new).collect(Collectors.toList());
    }

    @PostMapping
    public CommentResponse createComment(@RequestBody CommentRequest commentRequest) {
        return new CommentResponse(commentService.createComment(commentRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

}
