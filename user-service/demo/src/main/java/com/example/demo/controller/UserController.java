package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.excel.ReadDataFromExcel;
import com.example.demo.model.ErrorResponse;
import com.example.demo.exception.UserResponseException;
import com.example.demo.model.UserRequest;
import com.example.demo.model.UserResponse;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/multipart")
    public ResponseEntity<?> uploadUserDetailsExcel(@RequestParam("file") MultipartFile file) {

        if(ReadDataFromExcel.checkExcelFormat(file)) {
            userService.save(file);
            return ResponseEntity.ok(Map.of("message","file is upload"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("uplod excel file");

    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse userResponse = this.userService.create(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (IllegalArgumentException e) {
            String errorMessage = "User creation failed: " + e.getMessage();
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = "Error creating user due to data integrity violation: " + e.getMessage();
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT,errorMessage);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } catch (Exception e) {
            String errorMessage = "Error creating user: " + e.getMessage();
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try{
            UserResponse userResponse = userService.findUserById(id);
            return ResponseEntity.ok(userResponse);
        } catch(UserResponseException e){
            String errorMessage = "User not found ";
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        try {
            UserResponse userResponse = userService.updateUserById(id, userRequest);
            return ResponseEntity.ok().body(userResponse);
        } catch (UserResponseException e) {
            String errorMessage = "User not found. Wrong credentials";
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            String errorMessage = "Error updating user";
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        try {
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (UserResponseException e){
            String errorMessage = "User not found. Wrong credentials";
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,errorMessage);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<?>> getAllUsers(){
        try{
            List<UserResponse> list = userService.getAllUsers();
            if(list.size()<=0){
                String errorMessage = "No users found";
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,errorMessage);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList(errorResponse));
            }
            return ResponseEntity.ok(list);
        } catch(Exception e){
            String errorMessage = "Error getting all user";
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(errorResponse));
        }
    }

    @GetMapping("/sort/{field}")
    public ResponseEntity<List<?>> findUserWithSorting(@PathVariable String field) {
        try{
            List<UserResponse> userResponses = userService.findUserWithSorting(field);
            return new ResponseEntity<>(userResponses, HttpStatus.OK);
        } catch(Exception exception){
            String errorMessage = "Error in sorting user";
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(errorResponse));
        }
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<?>> findUserWithPaging(@PathVariable int offset, @PathVariable int pageSize) {
        try{
            Page<UserResponse> userResponses = userService.findUserWithPaging(offset,pageSize);
            return new ResponseEntity<>(userResponses, HttpStatus.OK);
        } catch(Exception exception){
            String errorMessage = "Error in sorting user";
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((Page<?>) Collections.singletonList(errorResponse));
        }
    }

    @GetMapping("/pagination/{offset}/{pageSize}/{field}")
    public ResponseEntity<Page<?>> PaginationAndSorting(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {
        try{
            Page<UserResponse> userResponses = userService.PaginationAndSorting(offset,pageSize,field);
            return new ResponseEntity<>(userResponses, HttpStatus.OK);
        } catch(Exception exception){
            String errorMessage = "Error in sorting user";
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((Page<?>) Collections.singletonList(errorResponse));
        }
    }
    
}
