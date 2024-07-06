package com.jeevanraksha.user_management_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeevanraksha.user_management_service.entity.User;

public interface UserRepository extends JpaRepository<User, UUID>{
    User findByUsername(String username);
}
