package com.learning.service;

import com.learning.request.AdminSignUpRequest;
import com.learning.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    UserResponse adminSignup(AdminSignUpRequest adminSignUpRequest);
}
