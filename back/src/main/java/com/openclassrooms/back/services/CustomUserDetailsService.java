package com.openclassrooms.back.services;

import com.openclassrooms.back.exceptions.NotFoundException;
import com.openclassrooms.back.models.User;
import com.openclassrooms.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Charge un utilisateur à partir de son email.
     * @param email l'email de l'utilisateur
     * @return les détails de l'utilisateur
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User with emeil " + email + " not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList() // Pas de rôles pour l'instant
        );
    }

    /**
     * Charge un utilisateur à partir de son email ou de son nom d'utilisateur.
     * @param emailOrName l'email ou le nom d'utilisateur de l'utilisateur
     * @return les détails de l'utilisateur
     */
    public UserDetails loadUserByEmailOrName(String emailOrName) throws NotFoundException {
        User user = userRepository.findByEmail(emailOrName);
        if (user == null) {
            user = userRepository.findByName(emailOrName);
            if (user == null) {
                throw new NotFoundException("User not found with email or name: " + emailOrName);
            }
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList() // Pas de rôles pour l'instant
        );
    }
}
