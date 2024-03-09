package com.example.demo.utility;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UserEntity;
import com.example.demo.enums.Gender;
import com.example.demo.model.UserRequest;
import com.example.demo.model.UserResponse;

@Component
public class Converter {
    public UserEntity requestToEntity(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userRequest, userEntity);
        userEntity.setGender(Gender.valueOf(userRequest.getGender()));
        return userEntity;
    }

    public UserResponse entityToResponse(UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(userEntity, userResponse);
        userResponse.setGender(String.valueOf(userEntity.getGender()));
        return userResponse;
    }

    public UserEntity updateEntity(UserRequest userRequest, UserEntity userEntity) {
        BeanUtils.copyProperties(userRequest, userEntity);
        userEntity.setGender(Gender.valueOf(userRequest.getGender()));
        return userEntity;
    }

}