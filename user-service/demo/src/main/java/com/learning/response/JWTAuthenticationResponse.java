package com.learning.response;

import lombok.Data;

@Data
public class JWTAuthenticationResponse {

    private String token, refreshToken;
    private long id;


}
