package com.learning.controller;

import com.learning.request.UserRequest;
import com.learning.response.UserResponse;
import com.learning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/multipart")
    public ResponseEntity<?> uploadUserDetailsExcel(@RequestParam("file") MultipartFile file) {
        userService.save(file);
        return ResponseEntity.ok().body("File uploaded successfully.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.findUserById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok().body(userService.updateUserById(id, userRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<?>> getAllUsers() {
        List<UserResponse> list = userService.getAllUsers();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList("No users found"));
        }
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/pagination/{offset}/{pageSize}/{field}")
    public ResponseEntity<Page<?>> paginationAndSorting(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String field) {
        return new ResponseEntity<>(userService.paginationAndSorting(offset, pageSize, field), HttpStatus.OK);
    }
}
