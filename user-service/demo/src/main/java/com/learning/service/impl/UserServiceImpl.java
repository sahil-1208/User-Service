package com.learning.service.impl;

import com.learning.entity.UserEntity;
import com.learning.exception.UserResponseException;
import com.learning.request.UserRequest;
import com.learning.response.UserResponse;
import com.learning.repository.UserRepository;
import com.learning.service.UserService;
import com.learning.utility.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Converter converter;

    private final ReadDataFromExcel readDataFromExcel;

    public void save(MultipartFile file) {
        try {
            List<UserEntity> listEmployee = readDataFromExcel.convertExcelToListOfUser(file.getInputStream());
            userRepository.saveAll(listEmployee);
        } catch (IOException exception) {
            log.error("Error saving data from Excel: ", exception);
            throw new RuntimeException("Error saving data from Excel");
        }
    }

    @Override
    public UserResponse findUserById(Long id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        return userEntityOptional.map(userEntity -> converter.entityToResponse(userEntity))
                .orElseThrow(() -> new UserResponseException("User Not Found"));
    }

    @Override
    public UserResponse updateUserById(Long id, UserRequest userRequest) {
        return userRepository.findById(id)
                .map(userEntity -> {
                    userEntity = converter.updateEntity(userRequest, userEntity);
                    userEntity = userRepository.save(userEntity);
                    return converter.entityToResponse(userEntity);
                })
                .orElseThrow(() -> new UserResponseException("User Not Found"));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.findById(id)
                .map(userEntity -> {
                    userRepository.deleteById(id);
                    log.info("Deletion of id {} is successful", id);
                    return userEntity;
                })
                .orElseThrow(() -> new UserResponseException("User Not Found"));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        if (userEntities.isEmpty()) {
            throw new UserResponseException("No users found");
        }
        log.info("Users are displayed");
        return userEntities.stream().map(userEntity -> converter.entityToResponse(userEntity))
                .collect(Collectors.toList());
    }


    public Page<UserResponse> paginationAndSorting(int offset, int pageSize, String field) {
        if (offset < 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Offset must be non-negative and pageSize must be greater than zero");
        }
        if (field == null || field.isEmpty()) {
            throw new IllegalArgumentException("Sorting field must be provided");
        }
        Page<UserEntity> userEntities = userRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        Page<UserResponse> userResponses = userEntities.map(converter::entityToResponse);
        log.info("Displaying all users with pagesize = " + pageSize + " and sorting on basis of " + field);
        return userResponses;
    }

}