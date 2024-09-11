package com.openclassrooms.back.controllers;

import com.openclassrooms.back.dto.CommentRequest;
import com.openclassrooms.back.dto.CommentResponse;
import com.openclassrooms.back.services.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Comments", description = "Endpoints for managing comments")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    private CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    /**
     * Récupère un commentaire par son identifiant
     * @param id identifiant du commentaire
     * @return commentaire
     */
    @GetMapping("/{id}")
    public CommentResponse getCommentById(@PathVariable Long id) {
        return new CommentResponse(commentService.getCommentById(id));
    }

    /**
     * Récupère les commentaires d'un post
     * @param postId identifiant du post
     * @return liste des commentaires
     */
    @GetMapping("/post/{postId}")
    public List<CommentResponse> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId).stream().map(CommentResponse::new).collect(Collectors.toList());
    }

    /**
     * Crée un commentaire
     * @param commentRequest requête de création de commentaire
     * @return commentaire créé
     */
    @PostMapping
    public CommentResponse createComment(@RequestBody @Valid CommentRequest commentRequest) {
        return new CommentResponse(commentService.createComment(commentRequest));
    }

    /**
     * Supprime un commentaire
     * @param id identifiant du commentaire
     */
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

}
