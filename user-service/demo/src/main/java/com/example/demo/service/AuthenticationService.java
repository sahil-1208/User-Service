package com.example.demo.service;

import com.example.demo.dto.JWTAuthenticationResponse;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.SignInRequest;
import com.example.demo.entity.UserEntity;
import com.example.demo.model.UserResponse;

public interface AuthenticationService {

    UserEntity signup(SignUpRequest signUpRequest);

    JWTAuthenticationResponse signIn(SignInRequest signinRequest);

    JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
