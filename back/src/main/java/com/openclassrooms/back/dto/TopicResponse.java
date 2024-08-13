package com.openclassrooms.back.dto;

import com.openclassrooms.back.models.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TopicResponse {
    private Long id;
    private String title;
    private String content;

    public TopicResponse(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.content = topic.getContent();
    }
}
