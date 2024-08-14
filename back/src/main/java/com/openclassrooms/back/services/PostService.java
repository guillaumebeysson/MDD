package com.openclassrooms.back.services;

import com.openclassrooms.back.dto.PostRequest;
import com.openclassrooms.back.models.Post;
import com.openclassrooms.back.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    /**
     * Récupère tous les posts
     * @return la liste des posts
     */
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    /**
     * Récupère un post par son id
     * @param id l'id du post
     * @return le post
     */
    public Post getPostById(Long id) {
        return postRepository.findById(id).get();
    }

    /**
     * Récupère tous les posts d'un topic
     * @param topicId l'id du topic
     * @return la liste des posts
     */
    public List<Post> getPostsByTopicId(Long topicId) {
        return postRepository.findByTopicId(topicId);
    }

    /**
     * Crée un post
     * @param postRequest les informations du post
     * @return le post créé
     */
    public Post createPost(PostRequest postRequest) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setUser(userService.getUserById(postRequest.getUserId()));
        post.setTopic(topicService.getTopicById(postRequest.getTopicId()));
        return postRepository.save(post);
    }

    /**
     * Met à jour un post
     * @param post le post à mettre à jour
     * @return le post mis à jour
     */
    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    /**
     * Supprime un post
     * @param id l'id du post
     */
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
