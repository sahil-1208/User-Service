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


    @Override
    public UserResponse create(UserRequest userRequest) {
        UserResponse userResponse = null;

        try {
            if (Objects.nonNull(userRequest)) {
                UserEntity userEntity = converter.requestToEntity(userRequest);
                userEntity = userRepository.save(userEntity);
                userResponse = converter.entityToResponse(userEntity);
                log.info("User created successfully. User ID: {}", userEntity.getId());
            } else {
                log.error("User creation failed. UserRequest is null.");
            }
        } catch (DataIntegrityViolationException e) {
                log.error("Error creating user due to data integrity violation. Details: {}", e.getMessage());
                // Handle the duplicate value entries
            } catch (Exception e) {
                log.error("Error creating user. Details: {}", e.getMessage());
            }

        return userResponse;
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
            List<UserResponse> userResponses = new ArrayList<>(userEntities.size());

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

    @Override
    public UserEntity registerUser(UserEntity userEntity) throws Exception
    {
        if(userEntity !=null)
        {
            return userRepository.save(userEntity);
        }
        throw new Exception("User is null");
    }

    @Override
    public Page getUserByPage(int pageIndex, int pageSize, String field)
    {
        Sort sort= Sort.by(Sort.Direction.ASC,field);
        Pageable pageReq= PageRequest.of(pageIndex, pageSize, sort);
        return userRepository.findAll(pageReq);
    }

}
