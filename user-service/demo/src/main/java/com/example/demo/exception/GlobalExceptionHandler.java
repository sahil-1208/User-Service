package com.example.demo.exception;

import com.example.demo.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class GlobalExceptionHandler {

    @ExceptionHandler(UserResponseException.class)
    public ResponseEntity<ErrorResponse> handleCustomerResponseException(UserResponseException exception) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
