package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ErrorResponse extends RuntimeException {

    private HttpStatus status;
    private String message;

    public ErrorResponse(HttpStatus status, String message) {
        super(String.format("%s not found with %s: '%s' ", status,message));
        this.status = status;
        this.message = message;
    }

}
