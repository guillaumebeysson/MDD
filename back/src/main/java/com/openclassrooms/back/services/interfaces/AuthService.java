package com.openclassrooms.back.services.interfaces;

import com.openclassrooms.back.dto.AuthRequest;
import com.openclassrooms.back.dto.RegisterRequest;


public interface AuthService {

    /**
     * Authentifie un utilisateur et retourne un token JWT
     * @param authRequest requête d'authentification
     * @return token JWT
     */
    String authenticateUser(AuthRequest authRequest);

    /**
     * Enregistre un nouvel utilisateur
     * @param registerRequest requête d'enregistrement
     * @return token JWT
     */
    String registerUser(RegisterRequest registerRequest);

}
