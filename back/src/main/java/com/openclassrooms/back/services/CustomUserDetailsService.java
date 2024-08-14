package com.openclassrooms.back.services;

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
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList() // Pas de rôles pour l'instant
        );
    }

    /**
     * Charge un utilisateur à partir de son email ou de son nom d'utilisateur.
     * @param usernameOrName l'email ou le nom d'utilisateur de l'utilisateur
     * @return les détails de l'utilisateur
     */
    public UserDetails loadUserByUsernameOrName(String usernameOrName) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(usernameOrName);
        if (user == null) {
            user = userRepository.findByName(usernameOrName);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + usernameOrName);
            }
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList() // Pas de rôles pour l'instant
        );
    }
}
