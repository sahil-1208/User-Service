package com.example.demo.response;

import lombok.Data;

@Data
public class JWTAuthenticationResponse {

    private String token;
    private String refreshtoken;
    private long id;


}
