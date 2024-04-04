package com.learning;

import com.learning.entity.UserEntity;
import com.learning.enums.Gender;
import com.learning.enums.Role;
import com.learning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;


@SpringBootApplication
public class UserServiceApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        UserEntity adminAccount = userRepository.findByRole(Role.ADMIN);
        if (null == adminAccount) {
            // date, email, first_name, gender, location, mobile, name, password, role, second_name, username
            UserEntity user = new UserEntity();
            user.setEmail("admin@gmail.com");
            user.setDate(LocalDate.now());
            user.setGender(Gender.MALE);
            user.setLocation("adminLoc");
            user.setMobile("9999234589");
            user.setUsername("adminExample");
            user.setFirstName("admin");
            user.setSecondName("example");
            user.setRole(Role.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }

}
