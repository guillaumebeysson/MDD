package com.openclassrooms.back.exceptions;

public class BadRequestException extends RuntimeException{

        public BadRequestException(String message) {
            super(message);
        }
}
