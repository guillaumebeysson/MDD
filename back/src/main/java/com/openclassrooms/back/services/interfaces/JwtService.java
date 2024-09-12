package com.openclassrooms.back.services.interfaces;


import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

public interface JwtService {

    /**
     * Génère un token JWT.
     * @param username le nom d'utilisateur
     * @return le token JWT
     */
    String generateToken(String username);

    /**
     * Extrait le nom d'utilisateur du token.
     * @param token le token
     * @return le nom d'utilisateur
     */
    String extractUserName(String token);

    /**
     * Extrait une revendication du token.
     * @param token le token
     * @param claimsResolver la fonction de résolution des revendications
     * @param <T> le type de la revendication
     * @return la revendication
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    /**
     * Extrait les revendications du token.
     * @param token le token
     * @return les revendications
     */
    Claims extractAllClaims(String token);

    /**
     * Récupère la clé secrète pour signer le token.
     * @return la clé secrète
     */
    Key getSignInKey();

    /**
     * Vérifie si le token est valide.
     * @param token le token
     * @param userDetails les détails de l'utilisateur
     * @return true si le token est valide, false sinon
     */
    boolean isTokenValid(String token, UserDetails userDetails);

    /**
     * Vérifie si le token est expiré.
     * @param token le token
     * @return true si le token est expiré, false sinon
     */
    boolean isTokenExpired(String token);

    /**
     * Extrait la date d'expiration du token.
     * @param token le token
     * @return la date d'expiration
     */
    Date extractExpiration(String token);
}
