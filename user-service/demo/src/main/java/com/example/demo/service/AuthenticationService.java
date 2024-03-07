package com.example.demo.service;

import com.example.demo.dto.JWTAuthenticationResponse;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.dto.SignInRequest;
import com.example.demo.model.UserRequest;
import com.example.demo.model.UserResponse;

public interface AuthenticationService {

    UserResponse signup(UserRequest userRequest);

        // UserEntity signup(SignUpRequest signUpRequest);

    JWTAuthenticationResponse signIn(SignInRequest signinRequest);

    JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
