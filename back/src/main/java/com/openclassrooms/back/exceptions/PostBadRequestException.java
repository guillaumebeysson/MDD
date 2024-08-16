package com.openclassrooms.back.exceptions;

public class PostBadRequestException extends RuntimeException{

        public PostBadRequestException(String message) {
            super(message);
        }
}
