package com.openclassrooms.back.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ErrorDetails {
    private Date timestamp;
    private HttpStatus status;
    private String message;
    private String details;

    public ErrorDetails(HttpStatus status, String message, String details) {
        this.timestamp = new Date();
        this.status = status;
        this.message = message;
        this.details = details;
    }

    // getters and setters
}
