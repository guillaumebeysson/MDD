package com.openclassrooms.back.services;

import com.openclassrooms.back.dto.PostRequest;
import com.openclassrooms.back.exceptions.BadRequestException;
import com.openclassrooms.back.exceptions.NotFoundException;
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
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post with id " + id + " not found"));
    }


    /**
     * Récupère tous les posts d'un topic
     * @param topicId l'id du topic
     * @return la liste des posts
     */
    public List<Post> getPostsByTopicId(Long topicId) {
        if (!topicService.existsById(topicId)) {
            throw new NotFoundException("Posts with Topic id " + topicId + " not found");
        }

        List<Post> posts = postRepository.findByTopicId(topicId);

        // Si aucun post n'est trouvé pour un topic existant
        if (posts.isEmpty()) {
            throw new NotFoundException("No posts found for topic with id " + topicId);
        }

        return posts;
    }

    /**
     * Crée un post
     * @param postRequest les informations du post
     * @return le post créé
     */
    public Post createPost(PostRequest postRequest) {
        // Vérification des champs obligatoires
        if (postRequest.getTitle().isEmpty() || postRequest.getContent().isEmpty()) {
            throw new BadRequestException("Title and content must not be null");
        }

        if (!userService.existsById(postRequest.getUserId())) {
            throw new NotFoundException("User with id " + postRequest.getUserId() + " not found");
        }

        if (!topicService.existsById(postRequest.getTopicId())) {
            throw new NotFoundException("Topic with id " + postRequest.getTopicId() + " not found");
        }

        // Création du post
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
        if (!postRepository.existsById(post.getId())) {
            throw new NotFoundException("Post with id " + post.getId() + " not found");
        }
        return postRepository.save(post);
    }

    /**
     * Supprime un post
     * @param id l'id du post
     */
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new NotFoundException("Post with id " + id + " not found");
        }
        postRepository.deleteById(id);
    }
}
