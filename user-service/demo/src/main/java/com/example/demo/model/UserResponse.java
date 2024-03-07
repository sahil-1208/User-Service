package com.example.demo.model;

import com.example.demo.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String name;
    private String email;
    private String mobile;
    private String username;
    private String location;
    private LocalDate date;
    private String gender;


}
