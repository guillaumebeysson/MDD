package com.openclassrooms.back.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

public interface CustomUserDetailsService{

    /**
     * Charge un utilisateur à partir de son email.
     * @param email l'email de l'utilisateur
     * @return les détails de l'utilisateur
     */
    UserDetails loadUserByUsername(String email);

    /**
     * Charge un utilisateur à partir de son email ou de son nom d'utilisateur.
     * @param emailOrName l'email ou le nom d'utilisateur de l'utilisateur
     * @return les détails de l'utilisateur
     */
    UserDetails loadUserByEmailOrName(String emailOrName);
}
