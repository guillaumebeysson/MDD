package com.openclassrooms.back.controllers;

import com.openclassrooms.back.dto.PostRequest;
import com.openclassrooms.back.dto.PostResponse;
import com.openclassrooms.back.models.Post;
import com.openclassrooms.back.repositories.PostRepository;
import com.openclassrooms.back.services.PostService;
import com.openclassrooms.back.services.TopicService;
import com.openclassrooms.back.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Endpoints for managing posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts().stream().map(PostResponse::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PostResponse getPostById(@PathVariable Long id) {
        return new PostResponse(postService.getPostById(id));
    }

    @GetMapping("/topic/{topicId}")
    public List<PostResponse> getPostsByTopicId(@PathVariable Long topicId) {
        return postService.getPostsByTopicId(topicId).stream().map(PostResponse::new).collect(Collectors.toList());
    }

    @PostMapping
    public PostResponse createPost(@RequestBody PostRequest postRequest) {
        return new PostResponse(postService.createPost(postRequest));
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

}
