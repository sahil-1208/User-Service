package com.learning.service;
import com.learning.request.SignUpRequest;
import com.learning.response.JWTAuthenticationResponse;
import com.learning.request.RefreshTokenRequest;
import com.learning.request.SignInRequest;
import com.learning.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    UserResponse signup(SignUpRequest signUpRequest);

    JWTAuthenticationResponse signIn(SignInRequest signinRequest);

    JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
