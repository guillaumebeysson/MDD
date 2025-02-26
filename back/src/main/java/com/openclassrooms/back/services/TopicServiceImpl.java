package com.openclassrooms.back.services;

import com.openclassrooms.back.exceptions.NotFoundException;
import com.openclassrooms.back.models.Topic;
import com.openclassrooms.back.repositories.TopicRepository;
import com.openclassrooms.back.services.interfaces.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    /**
     * Récupère tous les topics
     * @return la liste des topics
     */
    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    /**
     * Récupère un topic par son id
     * @param id l'id du topic
     * @return le topic
     */
    @Override
    public Topic getTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Topic with id " + id + " not found"));
    }

    /**
     * Vérifie si un topic existe par son id
     * @param topicId l'id du topic
     * @return true si le topic existe, false sinon
     */
    @Override
    public boolean existsById(Long topicId) {
        return topicRepository.existsById(topicId);
    }
}
