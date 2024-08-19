package com.openclassrooms.back.services;

import com.openclassrooms.back.dto.CommentRequest;
import com.openclassrooms.back.exceptions.NotFoundException;
import com.openclassrooms.back.models.Comment;
import com.openclassrooms.back.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    /**
     * Récupère les commentaires d'un post
     * @param postId l'id du post
     * @return la liste des commentaires
     */
    public List<Comment> getCommentsByPostId(Long postId) {
        if (!postService.existsById(postId)) {
            throw new NotFoundException("Comments with Post id " + postId + " not found");
        }
        return commentRepository.findByPostId(postId);
    }

    /**
     * Récupère un commentaire par son id
     * @param id l'id du commentaire
     * @return le commentaire
     */
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment with id " + id + " not found"));
    }

    /**
     * Crée un commentaire
     * @param commentRequest les informations du commentaire
     * @return le commentaire créé
     */
    public Comment createComment(CommentRequest commentRequest) {
        if (commentRequest.getContent() == null || commentRequest.getContent().isEmpty()) {
            throw new NotFoundException("Content must not be null or empty");
        }

        if (!userService.existsById(commentRequest.getUserId())) {
            throw new NotFoundException("User with id " + commentRequest.getUserId() + " not found");
        }

        if (!postService.existsById(commentRequest.getPostId())) {
            throw new NotFoundException("Post with id " + commentRequest.getPostId() + " not found");
        }

        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setUser(userService.getUserById(commentRequest.getUserId()));
        comment.setPost(postService.getPostById(commentRequest.getPostId()));
        return commentRepository.save(comment);
    }

    /**
     * Supprime un commentaire
     * @param id l'id du commentaire
     */
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new NotFoundException("Comment with id " + id + " not found");
        }
        commentRepository.deleteById(id);
    }
}
