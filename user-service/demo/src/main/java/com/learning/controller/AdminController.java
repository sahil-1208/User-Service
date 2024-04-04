package com.learning.controller;

import com.learning.request.AdminSignUpRequest;
import com.learning.request.SignUpRequest;
import com.learning.response.UserResponse;
import com.learning.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hi Admin");
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> adminSignup(@RequestBody AdminSignUpRequest adminSignUpRequest) {
        return ResponseEntity.ok(adminService.adminSignup(adminSignUpRequest));
    }
}
