package com.learning.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String email, mobile, username, location, gender, firstName, secondName, password;
    private LocalDate date;
}
