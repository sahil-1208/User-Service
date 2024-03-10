package com.example.demo.utility;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UserEntity;
import com.example.demo.enums.Gender;
import com.example.demo.enums.Role;
import com.example.demo.model.UserRequest;
import com.example.demo.model.UserResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;


@Component
@RequiredArgsConstructor
public class Converter {

    private final PasswordEncoder passwordEncoder;

    public UserEntity requestToEntity(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userRequest, userEntity);
        userEntity.setGender(Gender.valueOf(userRequest.getGender()));
        userEntity.setRole(Role.USER);
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userEntity;
    }

    public UserResponse entityToResponse(UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userEntity, userResponse);
        userResponse.setGender(String.valueOf(userEntity.getGender()));
        userResponse.setRole(Role.valueOf(String.valueOf(userEntity.getRole())));
        return userResponse;
    }

    public UserEntity updateEntity(UserRequest userRequest, UserEntity userEntity) {
        BeanUtils.copyProperties(userRequest, userEntity);
        userEntity.setGender(Gender.valueOf(userRequest.getGender()));
        userEntity.setRole(Role.USER);
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userEntity;
    }

}