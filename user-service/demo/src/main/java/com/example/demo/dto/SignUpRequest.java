package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpRequest {

    private String name;
    private String email;
    private String mobile;
    private String username;
    private String location;
    private LocalDate date;
    private String gender;
    private String firstName;
    private String lastName;
    private String password;

}
