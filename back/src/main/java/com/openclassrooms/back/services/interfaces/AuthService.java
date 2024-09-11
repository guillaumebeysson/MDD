package com.openclassrooms.back.services.interfaces;

import com.openclassrooms.back.dto.AuthRequest;
import com.openclassrooms.back.dto.RegisterRequest;
import com.openclassrooms.back.exceptions.BadRequestException;
import com.openclassrooms.back.exceptions.ConflictException;
import com.openclassrooms.back.exceptions.UnauthorizedException;
import com.openclassrooms.back.models.User;
import com.openclassrooms.back.repositories.UserRepository;
import com.openclassrooms.back.services.CustomUserDetailsService;
import com.openclassrooms.back.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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
