package com.learning.service;

import com.learning.request.UserRequest;
import com.learning.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {
    public UserResponse findUserById(Long id);

    public UserResponse updateUserById(Long id, UserRequest userRequest);

    public void deleteById(Long id);

    public List<UserResponse> getAllUsers();

    public void save(MultipartFile file);

    public Page<UserResponse> paginationAndSorting(int offset, int pageSize, String field);

}
