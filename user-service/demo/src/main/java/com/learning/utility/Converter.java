package com.learning.utility;
import com.learning.request.AdminSignUpRequest;
import com.learning.request.SignUpRequest;
import org.springframework.stereotype.Component;

import com.learning.entity.UserEntity;
import com.learning.enums.Gender;
import com.learning.enums.Role;
import com.learning.request.UserRequest;
import com.learning.response.UserResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


@Component
@RequiredArgsConstructor
public class Converter {

    private final PasswordEncoder passwordEncoder;

    public UserEntity adminSignUpToEntity(AdminSignUpRequest adminSignUpRequest) {
        return UserEntity.builder()
                .email(adminSignUpRequest.getEmail())
                .mobile(adminSignUpRequest.getMobile())
                .username(adminSignUpRequest.getUsername())
                .location(adminSignUpRequest.getLocation())
                .date(adminSignUpRequest.getDate())
                .gender(Gender.valueOf(adminSignUpRequest.getGender()))
                .firstName(adminSignUpRequest.getFirstName())
                .secondName(adminSignUpRequest.getSecondName())
                .role(Role.ADMIN)
                .password(passwordEncoder.encode(adminSignUpRequest.getPassword()))
                .build();
    }

    public UserEntity signUpToEntity(SignUpRequest signUpRequest) {
        return UserEntity.builder()
                .email(signUpRequest.getEmail())
                .mobile(signUpRequest.getMobile())
                .username(signUpRequest.getUsername())
                .location(signUpRequest.getLocation())
                .date(signUpRequest.getDate())
                .gender(Gender.valueOf(signUpRequest.getGender()))
                .firstName(signUpRequest.getFirstName())
                .secondName(signUpRequest.getSecondName())
                .role(Role.USER)
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();
    }

    public UserResponse entityToResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .mobile(userEntity.getMobile())
                .username(userEntity.getUsername())
                .location((userEntity.getLocation()))
                .date(userEntity.getDate())
                .gender(String.valueOf(userEntity.getGender()))
                .firstName(userEntity.getFirstName())
                .secondName(userEntity.getSecondName())
                .role(Role.valueOf(String.valueOf(userEntity.getRole())))
                .build();
    }

    public UserEntity updateEntity(UserRequest userRequest, UserEntity userEntity) {
        return UserEntity.builder()
                .email(userRequest.getEmail())
                .mobile(userRequest.getMobile())
                .username(userRequest.getUsername())
                .location(userRequest.getLocation())
                .date(userRequest.getDate())
                .gender(Gender.valueOf(userRequest.getGender()))
                .firstName(userRequest.getFirstName())
                .secondName(userRequest.getSecondName())
                .role(Role.USER)
                .build();
    }

}