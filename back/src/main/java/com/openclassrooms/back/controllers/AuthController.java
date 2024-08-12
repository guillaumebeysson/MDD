package com.openclassrooms.back.controllers;

import com.openclassrooms.back.dto.AuthRequest;
import com.openclassrooms.back.dto.RegisterRequest;
import com.openclassrooms.back.dto.TokenResponse;
import com.openclassrooms.back.dto.UserResponse;
import com.openclassrooms.back.models.User;
import com.openclassrooms.back.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Authenticate a user and return a JWT token")
    @PostMapping("/login")
    public TokenResponse login(@RequestBody AuthRequest authRequest) {
        return new TokenResponse(authService.authenticateUser(authRequest));
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public TokenResponse register(@RequestBody @Valid RegisterRequest registerRequest) {
        return new TokenResponse(authService.registerUser(registerRequest));
    }

    @Operation(summary = "Get the current authenticated user")
    @GetMapping("/me")
    public UserResponse getCurrentUser() {
        User user = authService.getCurrentUser();
        return new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
