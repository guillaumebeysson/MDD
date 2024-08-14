package com.openclassrooms.back.services;

import com.openclassrooms.back.dto.CommentRequest;
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

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).get();
    }

    public Comment createComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        comment.setContent(commentRequest.getContent());
        comment.setUser(userService.getUserById(commentRequest.getUserId()));
        comment.setPost(postService.getPostById(commentRequest.getPostId()));
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
