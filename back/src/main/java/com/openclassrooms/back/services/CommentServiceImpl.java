package com.openclassrooms.back.services;

import com.openclassrooms.back.dto.CommentRequest;
import com.openclassrooms.back.exceptions.NotFoundException;
import com.openclassrooms.back.models.Comment;
import com.openclassrooms.back.repositories.CommentRepository;

import com.openclassrooms.back.services.interfaces.CommentService;
import com.openclassrooms.back.services.interfaces.PostService;
import com.openclassrooms.back.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        if (!postService.existsById(postId)) {
            throw new NotFoundException("Comments with Post id " + postId + " not found");
        }
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment with id " + id + " not found"));
    }

    @Override
    public Comment createComment(CommentRequest commentRequest) {
        Assert.hasLength(commentRequest.getContent(), "Content must not be null or empty");

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

   @Override
    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new NotFoundException("Comment with id " + id + " not found");
        }
        commentRepository.deleteById(id);
    }
}
