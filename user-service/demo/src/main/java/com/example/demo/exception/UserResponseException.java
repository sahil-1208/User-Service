package com.example.demo.exception;

public class UserResponseException extends RuntimeException{
    public UserResponseException(String message) {
        super(message);
    }
}
