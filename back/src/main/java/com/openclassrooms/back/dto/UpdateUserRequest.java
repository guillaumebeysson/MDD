package com.openclassrooms.back.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @Email(message = "Email is not valid")
    private String email;

    private String name;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, one special character and must be at least 8 characters long")
    private String password;
}
