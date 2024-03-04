package com.example.demo.service.impl;

import com.example.demo.dto.JWTAuthenticationResponse;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.SignInRequest;
import com.example.demo.entity.UserEntity;
import com.example.demo.enums.Gender;
import com.example.demo.enums.Role;
import com.example.demo.model.UserResponse;
import com.example.demo.service.AuthenticationService;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTServiceImpl jwtService;

    public UserEntity signup(SignUpRequest signUpRequest){
        // id, date, email, first_name, gender, location, mobile, name, password, role, second_name, username
        UserEntity userEntity = new UserEntity();
        userEntity.setDate(signUpRequest.getDate());
        userEntity.setGender(Gender.valueOf(signUpRequest.getGender()));
        userEntity.setLocation(signUpRequest.getLocation());
        userEntity.setMobile(signUpRequest.getMobile());
        userEntity.setName(signUpRequest.getName());
        userEntity.setUsername(signUpRequest.getUsername());
        userEntity.setEmail(signUpRequest.getEmail());
        userEntity.setFirstName(signUpRequest.getFirstName());
        userEntity.setSecondName(signUpRequest.getLastName());
        userEntity.setRole(Role.USER);
        userEntity.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return userRepository.save(userEntity);
    }

    public JWTAuthenticationResponse signIn(SignInRequest signinRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
        var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(() ->new IllegalArgumentException("Invalid email or password "));
        var jwt= jwtService.generateToken(user);
        var refereshToken = jwtService.generateRefereshToken(new HashMap<>(),user);

        JWTAuthenticationResponse jWTAuthenticationResponse = new JWTAuthenticationResponse();
        jWTAuthenticationResponse.setToken(jwt);
        jWTAuthenticationResponse.setRefreshtoken(refereshToken);
        return jWTAuthenticationResponse;
    }

//    public JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
//        String userEmail = jwtService.extraxtUserName(refreshTokenRequest.getToken());
//        User user = userRepository.findByEmail(userEmail).orElseThrow();
//        if(jwtService.isTokenValid(refreshTokenRequest.getToken(),user)){
//            var jwt = jwtService.generateToken(user);
//
//            JWTAuthenticationResponse jWTAuthenticationResponse = new JWTAuthenticationResponse();
//            jWTAuthenticationResponse.setToken(jwt);
//            jWTAuthenticationResponse.setRefreshtoken(refreshTokenRequest.getToken());
//            return jWTAuthenticationResponse;
//        }

   // }
   public JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
       String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
       UserEntity userEntity = userRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("User not found"));
       if(jwtService.isTokenValid(refreshTokenRequest.getToken(), userEntity)){
           String jwt = jwtService.generateToken(userEntity);

           JWTAuthenticationResponse jWTAuthenticationResponse = new JWTAuthenticationResponse();
           jWTAuthenticationResponse.setToken(jwt);
           jWTAuthenticationResponse.setRefreshtoken(refreshTokenRequest.getToken());
           return jWTAuthenticationResponse;
       } else {
           throw new IllegalArgumentException("Invalid token");
       }
   }

}


