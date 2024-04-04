package com.learning.response;


import com.learning.enums.Role;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String email, mobile, username, location, gender, firstName, secondName;
    private LocalDate date;
    private Role role;

}
