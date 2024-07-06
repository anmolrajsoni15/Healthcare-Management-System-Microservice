package com.jeevanraksha.gateway.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeevanraksha.gateway.entity.User;

public interface UserRepository extends JpaRepository<User, UUID>{
    User findByUsername(String username);
}
