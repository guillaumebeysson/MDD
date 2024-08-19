package com.openclassrooms.back.services;

import com.openclassrooms.back.dto.AuthRequest;
import com.openclassrooms.back.dto.RegisterRequest;
import com.openclassrooms.back.exceptions.BadRequestException;
import com.openclassrooms.back.exceptions.ConflictException;
import com.openclassrooms.back.exceptions.UnauthorizedException;
import com.openclassrooms.back.models.User;
import com.openclassrooms.back.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authentifie un utilisateur et retourne un token JWT
     * @param authRequest requête d'authentification
     * @return token JWT
     */
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
                userDetails = customUserDetailsService.loadUserByUsername(emailOrUsername);  // Charger par email
            } else {
                // Sinon, on suppose que c'est un nom d'utilisateur
                userDetails = customUserDetailsService.loadUserByEmailOrName(emailOrUsername);  // Charger par nom d'utilisateur
            }
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid credentials provided");
        }

        if (userDetails == null) {
            throw new UnauthorizedException("Invalid credentials provided");
        }


        // Authentification réussie
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), authRequest.getPassword())
        );
            return jwtService.generateToken(userDetails.getUsername());
    }

    /**
     * Enregistre un nouvel utilisateur
     * @param registerRequest requête d'enregistrement
     * @return token JWT
     */
    public String registerUser(RegisterRequest registerRequest) {

        if (registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty() ||
                registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty() ||
                registerRequest.getName() == null) {
            throw new ConflictException("User with email " + registerRequest.getEmail() + " already exists");
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

}
