package com.learning.service.impl;
import com.learning.request.SignUpRequest;
import com.learning.response.JWTAuthenticationResponse;
import com.learning.request.RefreshTokenRequest;
import com.learning.request.SignInRequest;
import com.learning.entity.UserEntity;
import com.learning.response.UserResponse;
import com.learning.service.AuthenticationService;
import com.learning.repository.UserRepository;
import com.learning.utility.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final Converter converter;

    private final JWTServiceImpl jwtService;


    public UserResponse signup(SignUpRequest signUpRequest) {
        if (Objects.isNull(signUpRequest)) {
            log.error("User creation failed. UserRequest is null.");
            throw new IllegalArgumentException("SignUp cannot be null or empty.");
        }
        try {
            UserEntity userEntity = converter.signUpToEntity(signUpRequest);
            userEntity = userRepository.save(userEntity);
            log.info("User created successfully. User ID: {}", userEntity.getId());
            return converter.entityToResponse(userEntity);

        } catch (DataIntegrityViolationException exception) {
            log.error("Error creating user due to data integrity violation:", exception);
            throw new DataIntegrityViolationException("Error creating user due to data integrity violation: ");
        } catch (Exception exception) {
            log.error("Error creating user:", exception);
            throw new RuntimeException("Error creating user: ");
        }
    }

    public JWTAuthenticationResponse signIn(SignInRequest signinRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
        var user = userRepository.findByUsername(signinRequest.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password "));
        var jwt = jwtService.generateToken(user);
        var refereshToken = jwtService.generateRefereshToken(new HashMap<>(), user);

        JWTAuthenticationResponse jWTAuthenticationResponse = new JWTAuthenticationResponse();
        jWTAuthenticationResponse.setToken(jwt);
        jWTAuthenticationResponse.setRefreshToken(refereshToken);
        jWTAuthenticationResponse.setId(user.getId());
        return jWTAuthenticationResponse;
    }


    public JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String username = jwtService.extractUsername(refreshTokenRequest.getToken());
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), userEntity)) {
            String jwt = jwtService.generateToken(userEntity);

            JWTAuthenticationResponse jWTAuthenticationResponse = new JWTAuthenticationResponse();
            jWTAuthenticationResponse.setToken(jwt);
            jWTAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jWTAuthenticationResponse;
        } else {
            throw new IllegalArgumentException("Invalid token");
        }
    }

}


