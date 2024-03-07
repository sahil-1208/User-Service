package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserResponseException;
import com.example.demo.model.UserRequest;
import com.example.demo.model.UserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.utility.Converter;
import com.example.demo.excel.ReadDataFromExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Converter converter;

    public void save(MultipartFile file) {
        try {
            List<UserEntity> listEmployee = ReadDataFromExcel.convertExcelToListOfUser(file.getInputStream());
            userRepository.saveAll(listEmployee);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public UserResponse create(UserRequest userRequest) {
        try {
            if (Objects.isNull(userRequest)) {
                log.error("User creation failed. UserRequest is null.");
                throw new IllegalArgumentException("UserRequest cannot be null.");
            }

            UserEntity userEntity = converter.requestToEntity(userRequest);
            userEntity = userRepository.save(userEntity);
            log.info("User created successfully. User ID: {}", userEntity.getId());
            return converter.entityToResponse(userEntity);

        } catch (DataIntegrityViolationException e) {
            log.error("Error creating user due to data integrity violation: {}", e.getMessage());
            throw new DataIntegrityViolationException("Error creating user due to data integrity violation.");
        } catch (IllegalArgumentException e) {
            log.error("Error creating user: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage());
            throw new RuntimeException("Error creating user.");
        }
    }


    @Override
    public UserResponse findUserById(Long id) {
        try {
            Optional<UserEntity> userEntityOptional = userRepository.findById(id);
            if (userEntityOptional.isPresent()) {
                return converter.entityToResponse(userEntityOptional.get());
            } else {
                throw new UserResponseException("User Not Found");
            }
        } catch (UserResponseException e) {
            log.error("User id {} not found. Details: {}", id, e.getMessage());
            System.err.println("UserResponseException: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public UserResponse updateUserById(Long id, UserRequest userRequest) {
        try {
            return userRepository.findById(id)
                    .map(userEntity -> {
                        userEntity = converter.updateEntity(userRequest, userEntity);
                        userEntity = userRepository.save(userEntity);
                        return converter.entityToResponse(userEntity);
                    })
                    .orElseThrow(() -> new UserResponseException("User Not Found"));
        } catch (UserResponseException e) {
            log.error("User id {} not found during update. Details: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error updating user with id {}. Details: {}", id, e.getMessage());
            throw new UserResponseException("Error updating user");
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Optional<UserEntity> userEntityOptional = userRepository.findById(id);
            if (userEntityOptional.isPresent()) {
                userRepository.deleteById(id);
                 log.info("Deletion of id {} is successful", id);
            } else {
                throw new UserResponseException("User Not Found");
            }
        } catch (UserResponseException e) {
            log.error("User id {} not found", id);
            System.err.println("UserResponseException: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<UserResponse> getAllUsers() {
        try {
            List<UserEntity> userEntities = userRepository.findAll();
            List<UserResponse> userResponses = new ArrayList<>();

            for (UserEntity userEntity : userEntities) {
                userResponses.add(converter.entityToResponse(userEntity));
            }
            log.info("Users are displayed");
            return userResponses;
        } catch (Exception e) {
            log.error("Error retrieving all users. Details: {}", e.getMessage());
            throw new UserResponseException("Error retrieving all users");
        }
    }

    public List<UserResponse> findUserWithSorting(String field){
        try{
            List<UserEntity> userEntities = userRepository.findAll(Sort.by(Sort.Direction.ASC,field));
            List<UserResponse> userResponses = new ArrayList<>();

            for (UserEntity userEntity : userEntities) {
                userResponses.add(converter.entityToResponse(userEntity));
            }
            log.info("Users are sorted according to "+ field);
            return userResponses;
        } catch(Exception exception){
            log.error("Error retrieving all users. Details: {}", exception.getMessage());
            throw new UserResponseException("Error retrieving all users");
        }
    }

    public Page<UserResponse> findUserWithPaging(int offset, int pageSize){
        try{
            Page<UserEntity> userEntities = userRepository.findAll(PageRequest.of(offset, pageSize));
            Page<UserResponse> userResponses = userEntities.map(converter::entityToResponse);
            log.info("Displaying all users with pagesize = "+pageSize);
            return userResponses;
        } catch(Exception exception){
            log.error("Error retrieving all users. Details: {}", exception.getMessage());
            throw new UserResponseException("Error retrieving all users");
        }
    }

    public Page<UserResponse> PaginationAndSorting(int offset, int pageSize, String field){
        try{
            Page<UserEntity> userEntities = userRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
            Page<UserResponse> userResponses = userEntities.map(converter::entityToResponse);
            log.info("Displaying all users with pagesize = "+pageSize + " and sorting on basis of "+field);
            return userResponses;
        } catch(Exception exception){
            log.error("Error retrieving all users. Details: {}", exception.getMessage());
            throw new UserResponseException("Error retrieving all users");
        }
    }

}


