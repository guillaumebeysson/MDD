package com.openclassrooms.back.repositories;

import com.openclassrooms.back.models.Post;
import com.openclassrooms.back.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTopicId(Long topicId);
    List<Post> findByTopicIn(List<Topic> topics);
}
