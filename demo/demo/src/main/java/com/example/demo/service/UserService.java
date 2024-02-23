package com.example.demo.service;

import com.example.demo.model.UserRequest;
import com.example.demo.model.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public UserResponse create(UserRequest userRequest);
    public UserResponse findUserById(Long id);
    public UserResponse updateUserById(Long id, UserRequest userRequest);
    public void deleteById(Long id);
    public List<UserResponse> getAllUsers();

}
