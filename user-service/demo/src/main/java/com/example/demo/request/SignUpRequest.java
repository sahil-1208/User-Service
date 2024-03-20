package com.example.demo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

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
