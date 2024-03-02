package com.example.demo.repository;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {

    public String extraxtUserName(String token);

    public String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefereshToken(Map<String,Object> extraClaims, UserDetails userDetails);
}
