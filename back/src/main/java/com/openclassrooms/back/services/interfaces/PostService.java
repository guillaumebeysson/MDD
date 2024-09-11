package com.openclassrooms.back.services.interfaces;

import com.openclassrooms.back.dto.PostRequest;
import com.openclassrooms.back.models.Post;

import java.util.List;


public interface PostService {


    /**
     * Récupère tous les posts
     * @return la liste des posts
     */
    List<Post> getAllPosts();

    /**
     * Récupère un post par son id
     * @param id l'id du post
     * @return le post
     */
    Post getPostById(Long id);

    /**
     * Récupère tous les posts d'un topic
     * @param topicId l'id du topic
     * @return la liste des posts
     */
    List<Post> getPostsByTopicId(Long topicId);

    /**
     * Crée un post
     * @param postRequest les informations du post
     * @return le post créé
     */
    Post createPost(PostRequest postRequest);

    List<Post> getPostsByUserSubscriptions(Long userId);


    /**
     * Met à jour un post
     * @param post le post à mettre à jour
     * @return le post mis à jour
     */
    Post updatePost(Post post);

    /**
     * Supprime un post
     * @param id l'id du post
     */
    void deletePost(Long id);

    boolean existsById(Long postId);
}
