package com.example.demo.utility;

import com.example.demo.entity.UserEntity;
import com.example.demo.enums.Gender;
import com.example.demo.model.UserRequest;
import com.example.demo.model.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    public UserEntity requestToEntity(UserRequest userRequest) {

        UserEntity userEntity = new UserEntity();
        userEntity.setName(userRequest.getName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setMobile(userRequest.getMobile());
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setLocation(userRequest.getLocation());
        userEntity.setDate(userRequest.getDate());
        userEntity.setGender(Gender.valueOf(userRequest.getGender()));
        return userEntity;

    }

    public UserResponse entityToResponse(UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setName(userEntity.getName());
        userResponse.setEmail(userEntity.getEmail());
        userResponse.setMobile(userEntity.getMobile());
        userResponse.setPassword(userEntity.getPassword());
        userResponse.setUsername(userEntity.getUsername());
        userResponse.setLocation(userEntity.getLocation());
        userResponse.setDate(userEntity.getDate());
        userResponse.setGender(String.valueOf(userEntity.getGender()));
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
        return userEntity;

    }

}
