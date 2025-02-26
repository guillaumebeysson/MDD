package com.openclassrooms.back.repositories;

import com.openclassrooms.back.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository  extends JpaRepository<Topic, Long> {

}
