package com.example.demo.service;

import com.example.demo.request.SignUpRequest;
import com.example.demo.response.JWTAuthenticationResponse;
import com.example.demo.request.RefreshTokenRequest;
import com.example.demo.request.SignInRequest;
import com.example.demo.request.UserRequest;
import com.example.demo.response.UserResponse;

public interface AuthenticationService {

    UserResponse signup(SignUpRequest signUpRequest);

    JWTAuthenticationResponse signIn(SignInRequest signinRequest);

    JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
