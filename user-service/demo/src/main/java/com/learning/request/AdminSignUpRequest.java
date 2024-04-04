package com.learning.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class AdminSignUpRequest {

    private String email, mobile, username, location, gender, firstName, secondName, password;
    private LocalDate date;
}
