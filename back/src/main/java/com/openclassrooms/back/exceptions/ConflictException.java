package com.openclassrooms.back.exceptions;

public class ConflictException extends RuntimeException{

    public ConflictException(String message) {
        super(message);
    }
}
