package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String name;
    private String email;
    private String mobile;
    private String username;
    private String location;
    private LocalDate date;
    private String gender;
    private String firstName;
    private String secondName;
    private String password;
}
