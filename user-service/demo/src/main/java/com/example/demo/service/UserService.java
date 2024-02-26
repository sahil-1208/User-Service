package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.model.UserRequest;
import com.example.demo.model.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {

    public UserResponse create(UserRequest userRequest);
    public UserResponse findUserById(Long id);
    public UserResponse updateUserById(Long id, UserRequest userRequest);
    public void deleteById(Long id);
    public List<UserResponse> getAllUsers();
    public void save(MultipartFile file);


    public UserEntity registerUser(UserEntity userEntity) throws Exception;
    public Page<UserEntity> getUserByPage(int pageIndex, int pageSize, String field);

}
