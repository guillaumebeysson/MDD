package com.openclassrooms.back.services.interfaces;

import com.openclassrooms.back.dto.AuthRequest;
import com.openclassrooms.back.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;


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

    /**
     * Authentifie l'utilisateur, génère un token JWT et ajoute un cookie HttpOnly dans la réponse.
     *
     * @param authRequest La requête d'authentification
     * @param response    La réponse HTTP pour ajouter le cookie
     */
    void authenticateUserAndSetCookie(AuthRequest authRequest, HttpServletResponse response);

    /**
     * Enregistre un utilisateur, génère un token JWT et ajoute un cookie HttpOnly dans la réponse.
     *
     * @param registerRequest La requête d'enregistrement
     * @param response        La réponse HTTP pour ajouter le cookie
     */
    void registerUserAndSetCookie(RegisterRequest registerRequest, HttpServletResponse response);

    /**
     * Crée un cookie HttpOnly avec le token JWT et l'ajoute à la réponse.
     *
     * @param token    Le token JWT à ajouter dans le cookie
     * @param response La réponse HTTP pour ajouter le cookie
     */
    void addJwtCookieToResponse(String token, HttpServletResponse response);

    /**
     * Déconnecte l'utilisateur en supprimant le cookie JWT.
     *
     * @param response La réponse HTTP pour ajouter le cookie supprimé
     */
    void logoutAndClearCookie(HttpServletResponse response);

}
