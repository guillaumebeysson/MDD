package com.openclassrooms.back.services;

import com.openclassrooms.back.exceptions.NotFoundException;
import com.openclassrooms.back.exceptions.UnauthorizedException;
import com.openclassrooms.back.models.User;
import com.openclassrooms.back.repositories.UserRepository;
import com.openclassrooms.back.services.interfaces.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Charge un utilisateur à partir de son email.
     * @param email l'email de l'utilisateur
     * @return les détails de l'utilisateur
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new NotFoundException("User with email " + email + " not found");
            }
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.emptyList() // Pas de rôles pour l'instant
            );
        }
        catch (Exception e) {
            throw new UnauthorizedException("Invalid credentials provided");
        }


    }

    /**
     * Charge un utilisateur à partir de son email ou de son nom d'utilisateur.
     * @param emailOrName l'email ou le nom d'utilisateur de l'utilisateur
     * @return les détails de l'utilisateur
     */
    @Override
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
