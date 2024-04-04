package com.learning.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface JWTService {

    public String extractUsername(String token);

    public String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefereshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
