package com.openclassrooms.back.controllers;

import com.openclassrooms.back.dto.*;
import com.openclassrooms.back.services.AuthServiceImpl;
import com.openclassrooms.back.services.interfaces.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // @Operation(summary = "Authenticate a user and return a JWT token")
    //@PostMapping("/login")
    //@ResponseStatus(HttpStatus.CREATED)
    //public TokenResponse login(@RequestBody AuthRequest authRequest) {
    //    return new TokenResponse(authService.authenticateUser(authRequest));
    //}


    //@Operation(summary = "Register a new user")
    //@PostMapping("/register")
    //@ResponseStatus(HttpStatus.CREATED)
    //public TokenResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
    //    return new TokenResponse(authService.registerUser(registerRequest));
    //}

    /**
     * Authentifie un utilisateur et retourne un token JWT dans un cookie HttpOnly.
     *
     * @param authRequest Requête d'authentification
     * @param response    Réponse HTTP pour ajouter le cookie
     */
    @Operation(summary = "Authenticate a user and return a JWT token in a HttpOnly cookie")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public void login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        authService.authenticateUserAndSetCookie(authRequest, response);
    }

    /**
     * Enregistre un nouvel utilisateur et retourne un token JWT dans un cookie HttpOnly.
     *
     * @param registerRequest Requête d'enregistrement
     * @param response        Réponse HTTP pour ajouter le cookie
     */
    @Operation(summary = "Register a new user and return a JWT token in a HttpOnly cookie")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterRequest registerRequest, HttpServletResponse response) {
        authService.registerUserAndSetCookie(registerRequest, response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser() {
        return ResponseEntity.ok("User is authenticated");
    }

    /**
     * Déconnecte l'utilisateur en supprimant le cookie JWT.
     *
     * @param response La réponse HTTP pour ajouter le cookie supprimé
     */
    @Operation(summary = "Logout the user by clearing the JWT cookie")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> logout(HttpServletResponse response) {
        authService.logoutAndClearCookie(response);
        return ResponseEntity.ok("Logged out successfully");
    }
}
