package com.learning.request;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private String email, mobile, username, location, gender, firstName, secondName;
    private LocalDate date;
}
