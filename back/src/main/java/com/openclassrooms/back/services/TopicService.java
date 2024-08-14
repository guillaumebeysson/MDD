package com.openclassrooms.back.services;

import com.openclassrooms.back.models.Topic;
import com.openclassrooms.back.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    /**
     * Récupère tous les topics
     * @return la liste des topics
     */
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    /**
     * Récupère un topic par son id
     * @param id l'id du topic
     * @return le topic
     */
    public Topic getTopicById(Long id) {
        return topicRepository.findById(id).get();
    }
}
