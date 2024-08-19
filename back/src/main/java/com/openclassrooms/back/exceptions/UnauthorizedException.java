package com.openclassrooms.back.exceptions;

public class UnauthorizedException extends RuntimeException{

        public UnauthorizedException(String message) {
            super(message);
        }
}
