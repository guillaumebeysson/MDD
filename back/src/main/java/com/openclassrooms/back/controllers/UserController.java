package com.openclassrooms.back.controllers;

import com.openclassrooms.back.dto.UpdateUserRequest;
import com.openclassrooms.back.dto.UserResponse;
import com.openclassrooms.back.models.User;
import com.openclassrooms.back.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "Endpoints for user management")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Get the current authenticated user")
    @GetMapping("/me")
    public UserResponse getCurrentUser() {
        User user = userService.getCurrentUser();
        return new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());
    }

    @Operation(summary = "Update the current authenticated user's information")
    @PutMapping("/me")
    public UserResponse updateCurrentUser(@RequestBody @Valid UpdateUserRequest updateUserRequest) {
        User updatedUser = userService.updateUser(updateUserRequest);
        return new UserResponse(updatedUser.getId(), updatedUser.getEmail(), updatedUser.getName(), updatedUser.getCreatedAt(), updatedUser.getUpdatedAt());
    }
}
