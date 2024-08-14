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

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).get();
    }

    public List<Post> getPostsByTopicId(Long topicId) {
        return postRepository.findByTopicId(topicId);
    }

    public Post createPost(PostRequest postRequest) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setUser(userService.getUserById(postRequest.getUserId()));
        post.setTopic(topicService.getTopicById(postRequest.getTopicId()));
        return postRepository.save(post);
    }

    public Post updatePost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
