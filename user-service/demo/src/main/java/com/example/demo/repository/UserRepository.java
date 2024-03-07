package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long>  {
//    boolean existsByEmail(String email);
//
//    boolean existsByMobile(String mobile);
//
//    boolean existsByUsername(String username);
//
//    boolean existsByEmailOrMobileOrUsername(String email, String mobile, String username);
}
