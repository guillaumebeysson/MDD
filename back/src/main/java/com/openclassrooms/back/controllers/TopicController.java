package com.openclassrooms.back.controllers;

import com.openclassrooms.back.dto.TopicResponse;
import com.openclassrooms.back.services.TopicServiceImpl;
import com.openclassrooms.back.services.interfaces.TopicService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Topics", description = "Endpoints for managing topics")
@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "http://localhost:4200")
public class TopicController {

    private TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

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
