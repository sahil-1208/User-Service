package com.example.demo.model;

import com.example.demo.enums.Gender;
import com.example.demo.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String name;
    private String email;
    private String mobile;
    private String username;
    private String location;
    private LocalDate date;
    private String gender;
    private String firstName;
    private String secondName;
    private Role role;

}
