package com.openclassrooms.back.services;

import com.openclassrooms.back.dto.AuthRequest;
import com.openclassrooms.back.dto.RegisterRequest;
import com.openclassrooms.back.exceptions.BadRequestException;
import com.openclassrooms.back.exceptions.ConflictException;
import com.openclassrooms.back.exceptions.UnauthorizedException;
import com.openclassrooms.back.models.User;
import com.openclassrooms.back.repositories.UserRepository;
import com.openclassrooms.back.services.interfaces.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String authenticateUser(AuthRequest authRequest) {


        // Détermine si l'entrée est un email ou un nom d'utilisateur
        String emailOrUsername = authRequest.getEmailOrUsername();
        UserDetails userDetails;

        if (emailOrUsername == null || emailOrUsername.isEmpty() ||
                authRequest.getPassword() == null || authRequest.getPassword().isEmpty()) {
            throw new BadRequestException("Email/Username and password must not be null or empty");
        }

        try {
            if (emailOrUsername.contains("@")) {
                // Si l'entrée contient un '@', on suppose que c'est un email
                userDetails = customUserDetailsService.loadUserByUsername(emailOrUsername);
            } else {
                // Sinon, on suppose que c'est un nom d'utilisateur
                userDetails = customUserDetailsService.loadUserByEmailOrName(emailOrUsername);
            }
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid credentials provided");
        }

        if (userDetails == null) {
            throw new UnauthorizedException("Invalid credentials provided");
        }


        try {
            // Authentification réussie
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid credentials provided");
        }
            return jwtService.generateToken(userDetails.getUsername());
    }


    @Override
    public String registerUser(RegisterRequest registerRequest) {

        if (registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()){
            throw new BadRequestException("Email must not be null or empty");
        }

        if (registerRequest.getName() == null || registerRequest.getName().isEmpty()){
            throw new BadRequestException("Name must not be null or empty");
        }

        if (registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty()){
            throw new BadRequestException("Password must not be null or empty");
        }

        if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new ConflictException("User with email " + registerRequest.getEmail() + " already exists");
        }

        if (userRepository.findByName(registerRequest.getName()) != null) {
            throw new ConflictException("User with name " + registerRequest.getName() + " already exists");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmailOrUsername(registerRequest.getEmail());
        authRequest.setPassword(registerRequest.getPassword());
        return authenticateUser(authRequest);
    }

    /**
     * Authentifie l'utilisateur, génère un token JWT et ajoute un cookie HttpOnly dans la réponse.
     *
     * @param authRequest La requête d'authentification
     * @param response    La réponse HTTP pour ajouter le cookie
     */
    public void authenticateUserAndSetCookie(AuthRequest authRequest, HttpServletResponse response) {
        String token = authenticateUser(authRequest);
        addJwtCookieToResponse(token, response);
    }

    /**
     * Enregistre un utilisateur, génère un token JWT et ajoute un cookie HttpOnly dans la réponse.
     *
     * @param registerRequest La requête d'enregistrement
     * @param response        La réponse HTTP pour ajouter le cookie
     */
    public void registerUserAndSetCookie(RegisterRequest registerRequest, HttpServletResponse response) {
        String token = registerUser(registerRequest);
        addJwtCookieToResponse(token, response);
    }

    /**
     * Crée un cookie HttpOnly avec le token JWT et l'ajoute à la réponse.
     *
     * @param token    Le token JWT à ajouter dans le cookie
     * @param response La réponse HTTP pour ajouter le cookie
     */
    private void addJwtCookieToResponse(String token, HttpServletResponse response) {
        ResponseCookie jwtCookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(true) // Assurez-vous que l'application utilise HTTPS en production
                .path("/")
                .maxAge(24 * 60 * 60) // Durée de vie en secondes (ici, 1 jour)
                .sameSite("Strict")
                .build();

        response.addHeader("Set-Cookie", jwtCookie.toString());
    }

    /**
     * Déconnecte l'utilisateur en supprimant le cookie JWT.
     *
     * @param response La réponse HTTP pour ajouter le cookie supprimé
     */
    public void logoutAndClearCookie(HttpServletResponse response) {
        // Créer un cookie "token" avec une durée de vie de 0 pour le supprimer
        ResponseCookie jwtCookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(true) // Assurez-vous d'utiliser HTTPS en production
                .path("/")
                .maxAge(0) // Durée de vie 0 pour supprimer le cookie
                .sameSite("Strict")
                .build();

        // Ajouter le cookie supprimé à la réponse
        response.addHeader("Set-Cookie", jwtCookie.toString());
    }

}
