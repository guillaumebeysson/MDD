package com.openclassrooms.back.controllers;

import com.openclassrooms.back.dto.TopicResponse;
import com.openclassrooms.back.services.TopicService;
import com.openclassrooms.back.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Topics", description = "Endpoints for managing topics")
@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /**
     * Récupère un topic par son identifiant
     * @param id identifiant du topic
     * @return topic
     */
    @GetMapping("/{id}")
    public TopicResponse getTopicById(@PathVariable Long id) {
        return new TopicResponse(topicService.getTopicById(id));
    }

    /**
     * Récupère tous les topics
     * @return liste des topics
     */
    @GetMapping
    public List<TopicResponse> getAllTopics() {
        return topicService.getAllTopics().stream().map(TopicResponse::new).collect(Collectors.toList());
    }




}
