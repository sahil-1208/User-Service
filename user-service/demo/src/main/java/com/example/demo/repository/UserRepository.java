package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import com.example.demo.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long>  {

    Optional<UserEntity> findByEmail(String email);

    UserEntity findByRole(Role role);
}
