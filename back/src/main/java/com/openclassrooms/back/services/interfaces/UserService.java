package com.openclassrooms.back.services.interfaces;

import com.openclassrooms.back.dto.UpdateUserRequest;
import com.openclassrooms.back.exceptions.ConflictException;
import com.openclassrooms.back.exceptions.NotFoundException;
import com.openclassrooms.back.models.Topic;
import com.openclassrooms.back.models.User;
import com.openclassrooms.back.repositories.TopicRepository;
import com.openclassrooms.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    /**
     * Récupère l'utilisateur actuellement connecté
     * @return l'utilisateur actuellement connecté
     */
    User getCurrentUser();

    /**
     * Récupère un utilisateur par son id
     * @param id l'id de l'utilisateur
     * @return l'utilisateur
     */
    User getUserById(Long id);

    /**
     * Met à jour un utilisateur
     * @param updateUserRequest la requête de mise à jour
     * @return l'utilisateur mis à jour
     */
    User updateUser(UpdateUserRequest updateUserRequest);

    /**
     * Abonne l'utilisateur au topic donné
     * @param userId l'id de l'utilisateur
     * @param topicId l'id du topic
     */
    void subscribeToTopic(Long userId, Long topicId);

    /**
     * Désabonne l'utilisateur du topic donné
     * @param userId l'id de l'utilisateur
     * @param topicId l'id du topic
     */
    void unsubscribeFromTopic(Long userId, Long topicId);

    /**
     * Récupère les topics auxquels l'utilisateur est abonné
     * @param userId l'id de l'utilisateur
     * @return liste des topics abonnés
     */
    List<Topic> getSubscribedTopics(Long userId);

    boolean existsById(Long userId);
}
