package com.example.demo.service.impl;

import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserResponseException;
import com.example.demo.request.UserRequest;
import com.example.demo.response.UserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.utility.Converter;
import com.example.demo.excel.ReadDataFromExcel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Converter converter;

    @Autowired
    private ReadDataFromExcel readDataFromExcel;

    public void save(MultipartFile file) {
        try {
            List<UserEntity> listEmployee = readDataFromExcel.convertExcelToListOfUser(file.getInputStream());
            userRepository.saveAll(listEmployee);
        } catch (IOException exception) {
            log.error("Error saving data from Excel: {}", exception);
            throw new RuntimeException("Error saving data from Excel");
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
        } catch (UserResponseException exception) {
            log.error("User id {} not found. Details: {}", id, exception);
            throw exception;
        } catch (Exception exception) {
            log.error("Error finding user by id: {}", exception);
            throw new RuntimeException("Error finding user by id");
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
        } catch (UserResponseException exception) {
            log.error("User id {} not found during update. Details: {}", id, exception);
            throw exception;
        } catch (Exception exception) {
            log.error("Error updating user with id {}. Details: {}", id, exception);
            throw new RuntimeException("Error updating user");
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
        } catch (UserResponseException exception) {
            log.error("User id {} not found. Details: {}", id, exception);
            throw exception;
        } catch (Exception exception) {
            log.error("Error deleting user with id {}. Details: {}", id, exception);
            throw new RuntimeException("Error deleting user");
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
        } catch (Exception exception) {
            log.error("Error retrieving all users. Details: {}", exception);
            throw new RuntimeException("Error retrieving all users");
        }
    }

    public Page<UserResponse> PaginationAndSorting(int offset, int pageSize, String field) {
        try {
            Page<UserEntity> userEntities = userRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
            Page<UserResponse> userResponses = userEntities.map(converter::entityToResponse);
            log.info("Displaying all users with pagesize = " + pageSize + " and sorting on basis of " + field);
            return userResponses;
        } catch (Exception exception) {
            log.error("Error retrieving all users. Details: {}", exception);
            throw new UserResponseException("Error retrieving all users");
        }
    }
}