package com.learning.service.impl;

import com.learning.entity.UserEntity;
import com.learning.repository.UserRepository;
import com.learning.request.AdminSignUpRequest;
import com.learning.response.UserResponse;
import com.learning.service.AdminService;
import com.learning.utility.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    private Converter converter;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse adminSignup(AdminSignUpRequest adminSignUpRequest) {
        if (Objects.isNull(adminSignUpRequest)) {
            log.error("User creation failed. UserRequest is null.");
            throw new IllegalArgumentException("SignUp cannot be null or empty.");
        }
        try {
            UserEntity userEntity = converter.adminSignUpToEntity(adminSignUpRequest);
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
}
