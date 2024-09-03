package com.openclassrooms.back.controllers;

import com.openclassrooms.back.dto.PostRequest;
import com.openclassrooms.back.dto.PostResponse;
import com.openclassrooms.back.models.User;
import com.openclassrooms.back.services.PostService;
import com.openclassrooms.back.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Endpoints for managing posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    /**
     * Récupère tous les posts
     * @return liste des posts
     */
    @GetMapping
    public List<PostResponse> getAllPosts() {
        return postService.getAllPosts().stream().map(PostResponse::new).collect(Collectors.toList());
    }

    /**
     * Récupère un post par son identifiant
     * @param id identifiant du post
     * @return post
     */
    @GetMapping("/{id}")
    public PostResponse getPostById(@PathVariable Long id) {
        return new PostResponse(postService.getPostById(id));
    }

    /**
     * Récupère les posts d'un topic
     * @param topicId identifiant du topic
     * @return liste des posts du topic
     */
    @GetMapping("/topic/{topicId}")
    public List<PostResponse> getPostsByTopicId(@PathVariable Long topicId) {
        return postService.getPostsByTopicId(topicId).stream().map(PostResponse::new).collect(Collectors.toList());
    }

    /**
     * Récupère les posts des topics auxquels l'utilisateur actuel est abonné
     * @return liste des posts
     */
    @GetMapping("/interests")
    public List<PostResponse> getSubscribedPosts() {
        User currentUser = userService.getCurrentUser();
        return postService.getPostsByUserSubscriptions(currentUser.getId()).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Crée un post
     * @param postRequest requête de création de post
     * @return post créé
     */
    @PostMapping
    public PostResponse createPost(@RequestBody PostRequest postRequest) {
        return new PostResponse(postService.createPost(postRequest));
    }

    /**
     * Supprime un post
     * @param id identifiant du post
     */
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }



}
