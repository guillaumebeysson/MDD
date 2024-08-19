package com.openclassrooms.back.services;

import com.openclassrooms.back.dto.UpdateUserRequest;
import com.openclassrooms.back.exceptions.NotFoundException;
import com.openclassrooms.back.models.User;
import com.openclassrooms.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
            currentUser.setName(updateUserRequest.getName());
        }

        // Mise à jour de l'email si fourni, sinon garder l'email actuel
        if (updateUserRequest.getEmail() != null && !updateUserRequest.getEmail().isEmpty()) {
            currentUser.setEmail(updateUserRequest.getEmail());
        }

        // Mise à jour du mot de passe si fourni, sinon garder le mot de passe actuel
        if (updateUserRequest.getPassword() != null && !updateUserRequest.getPassword().isEmpty()) {
            currentUser.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }

        userRepository.save(currentUser);
        return currentUser;
    }

    public boolean existsById(Long userId) {
        return userRepository.existsById(userId);
    }
}
