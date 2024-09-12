package com.openclassrooms.back.services.interfaces;

import com.openclassrooms.back.models.Topic;

import java.util.List;

public interface TopicService {

    /**
     * Récupère tous les topics
     * @return la liste des topics
     */
    List<Topic> getAllTopics();

    /**
     * Récupère un topic par son id
     * @param id l'id du topic
     * @return le topic
     */
    Topic getTopicById(Long id);

    /**
     * Vérifie si un topic existe par son id
     * @param topicId l'id du topic
     * @return true si le topic existe, false sinon
     */
    boolean existsById(Long topicId);
}
