package com.example.demo.utility;

import com.example.demo.entity.UserEntity;
import com.example.demo.enums.Gender;
import com.example.demo.enums.Role;
import com.example.demo.model.UserRequest;
import com.example.demo.model.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Converter {

    private final PasswordEncoder passwordEncoder;

    public UserEntity requestToEntity(UserRequest userRequest) {

        UserEntity userEntity = new UserEntity();
        userEntity.setName(userRequest.getName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setMobile(userRequest.getMobile());
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setLocation(userRequest.getLocation());
        userEntity.setDate(userRequest.getDate());
        userEntity.setGender(Gender.valueOf(userRequest.getGender()));
        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setSecondName(userRequest.getSecondName());
        userEntity.setRole(Role.USER);
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userEntity;

    }

    public UserResponse entityToResponse(UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setName(userEntity.getName());
        userResponse.setEmail(userEntity.getEmail());
        userResponse.setMobile(userEntity.getMobile());
        userResponse.setUsername(userEntity.getUsername());
        userResponse.setLocation(userEntity.getLocation());
        userResponse.setDate(userEntity.getDate());
        userResponse.setGender(String.valueOf(userEntity.getGender()));
        userResponse.setFirstName(userEntity.getFirstName());
        userResponse.setSecondName(userEntity.getSecondName());
        userResponse.setRole(userEntity.getRole());
        return userResponse;

    }

    public UserEntity updateEntity(UserRequest userRequest, UserEntity userEntity ) {

        userEntity.setName(userRequest.getName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setMobile(userRequest.getMobile());
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setLocation(userRequest.getLocation());
        userEntity.setDate(userRequest.getDate());
        userEntity.setGender(Gender.valueOf(userRequest.getGender()));
        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setSecondName(userRequest.getSecondName());
        userEntity.setRole(Role.USER);
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        return userEntity;

    }

}
