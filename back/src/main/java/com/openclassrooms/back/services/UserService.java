package com.openclassrooms.back.services;

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

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TopicRepository topicRepository;

    /**
     * Récupère l'utilisateur actuellement connecté
     * @return l'utilisateur actuellement connecté
     */
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

    /**
     * Récupère un utilisateur par son id
     * @param id l'id de l'utilisateur
     * @return l'utilisateur
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    /**
     * Met à jour un utilisateur
     * @param updateUserRequest la requête de mise à jour
     * @return l'utilisateur mis à jour
     */
    public User updateUser(UpdateUserRequest updateUserRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email);

        if (currentUser == null) {
            throw new NotFoundException("User not found");
        }

        // Mise à jour du nom si fourni, sinon garder le nom actuel
        if (updateUserRequest.getName() != null && !updateUserRequest.getName().isEmpty()) {
            User userWithSameName = userRepository.findByName(updateUserRequest.getName());
            if (userWithSameName != null && !userWithSameName.getId().equals(currentUser.getId())) {
                throw new ConflictException("Name " + updateUserRequest.getName() + " is already used");
            }
        }

        // Vérification si l'email est déjà utilisé par un autre utilisateur
        if (updateUserRequest.getEmail() != null && !updateUserRequest.getEmail().isEmpty()) {
            User userWithSameEmail = userRepository.findByEmail(updateUserRequest.getEmail());
            if (userWithSameEmail != null && !userWithSameEmail.getId().equals(currentUser.getId())) {
                throw new ConflictException("Email " + updateUserRequest.getEmail() + " is already used");
            }
            currentUser.setEmail(updateUserRequest.getEmail());
        }

        // Mise à jour du mot de passe si fourni, sinon garder le mot de passe actuel
        if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }

        userRepository.save(currentUser);
        return currentUser;
    }

    /**
     * Abonne l'utilisateur au topic donné
     * @param userId l'id de l'utilisateur
     * @param topicId l'id du topic
     */
    public void subscribeToTopic(Long userId, Long topicId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException("Topic with id " + topicId + " not found"));

        if (user.getTopics().contains(topic)) {
            throw new ConflictException("User is already subscribed to this topic");
        }

        user.getTopics().add(topic);
        userRepository.save(user);
    }

    /**
     * Désabonne l'utilisateur du topic donné
     * @param userId l'id de l'utilisateur
     * @param topicId l'id du topic
     */
    public void unsubscribeFromTopic(Long userId, Long topicId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException("Topic with id " + topicId + " not found"));

        if (!user.getTopics().contains(topic)) {
            throw new NotFoundException("User is not subscribed to this topic");
        }

        user.getTopics().remove(topic);
        userRepository.save(user);
    }

    /**
     * Récupère les topics auxquels l'utilisateur est abonné
     * @param userId l'id de l'utilisateur
     * @return liste des topics abonnés
     */
    public List<Topic> getSubscribedTopics(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
        return user.getTopics();
    }

    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }
}
