package com.openclassrooms.back.services.interfaces;

import com.openclassrooms.back.dto.CommentRequest;
import com.openclassrooms.back.models.Comment;

import java.util.List;

public interface CommentService {

    /**
     * Récupère les commentaires d'un post
     * @param postId l'id du post
     * @return la liste des commentaires
     */
    List<Comment> getCommentsByPostId(Long postId);

    /**
     * Récupère un commentaire par son id
     * @param id l'id du commentaire
     * @return le commentaire
     */
    Comment getCommentById(Long id);

    /**
     * Crée un commentaire
     * @param commentRequest les informations du commentaire
     * @return le commentaire créé
     */
    Comment createComment(CommentRequest commentRequest);

    /**
     * Supprime un commentaire
     * @param id l'id du commentaire
     */
    void deleteComment(Long id);
}
