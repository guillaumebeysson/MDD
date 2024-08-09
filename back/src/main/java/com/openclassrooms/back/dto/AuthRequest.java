package com.openclassrooms.back.dto;

import lombok.Data;

@Data
public class AuthRequest {

    private String emailOrUsername;
    private String password;
}
