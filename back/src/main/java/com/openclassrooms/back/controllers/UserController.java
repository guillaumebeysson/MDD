package com.openclassrooms.back.controllers;

import com.openclassrooms.back.dto.TopicResponse;
import com.openclassrooms.back.dto.UpdateUserRequest;
import com.openclassrooms.back.dto.UserResponse;
import com.openclassrooms.back.models.User;
import com.openclassrooms.back.services.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "Endpoints for user management")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * Récupère l'utilisateur actuellement authentifié
     * @return utilisateur actuel
     */
    @Operation(summary = "Get the current authenticated user")
    @GetMapping()
    public UserResponse getCurrentUser() {
        User user = userService.getCurrentUser();
        return new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());
    }

    /**
     * Met à jour les informations de l'utilisateur actuellement authentifié
     * @param updateUserRequest requête de mise à jour de l'utilisateur
     * @return utilisateur mis à jour
     */
    @Operation(summary = "Update the current authenticated user's information")
    @PutMapping()
    public UserResponse updateCurrentUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        User updatedUser = userService.updateUser(updateUserRequest);
        return new UserResponse(updatedUser.getId(), updatedUser.getEmail(), updatedUser.getName(), updatedUser.getCreatedAt(), updatedUser.getUpdatedAt());
    }

    /**
     * Abonne l'utilisateur actuel à un topic
     * @param topicId l'id du topic
     */
    @PostMapping("/subscribe/{topicId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void subscribeToTopic(@PathVariable Long topicId) {
        User currentUser = userService.getCurrentUser();
        userService.subscribeToTopic(currentUser.getId(), topicId);
    }

    /**
     * Désabonne l'utilisateur actuel d'un topic
     * @param topicId l'id du topic
     */
    @DeleteMapping("/unsubscribe/{topicId}")
    public void unsubscribeFromTopic(@PathVariable Long topicId) {
        User currentUser = userService.getCurrentUser();
        userService.unsubscribeFromTopic(currentUser.getId(), topicId);
    }

    /**
     * Récupère les topics auxquels l'utilisateur actuel est abonné
     * @return liste des topics
     */
    @GetMapping("/subscriptions")
    public List<TopicResponse> getSubscribedTopics() {
        User currentUser = userService.getCurrentUser();
        return userService.getSubscribedTopics(currentUser.getId()).stream().map(TopicResponse::new).collect(Collectors.toList());
    }

}
