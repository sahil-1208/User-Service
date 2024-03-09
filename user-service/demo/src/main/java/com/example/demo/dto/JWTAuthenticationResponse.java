package com.example.demo.dto;

import lombok.Data;

@Data
public class JWTAuthenticationResponse {

    private String token;
    private String refreshtoken;


}
