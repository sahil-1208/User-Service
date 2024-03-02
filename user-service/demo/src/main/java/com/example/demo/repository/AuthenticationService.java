package com.example.demo.repository;

import com.example.demo.dto.JWTAuthenticationResponse;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.SigninRequest;
import com.example.demo.entity.UserEntity;

public interface AuthenticationService {

    UserEntity signup(SignUpRequest signUpRequest);

    JWTAuthenticationResponse signin(SigninRequest signinRequest);

    JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
