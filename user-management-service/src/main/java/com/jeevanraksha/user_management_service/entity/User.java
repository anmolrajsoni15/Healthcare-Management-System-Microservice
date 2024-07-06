package com.jeevanraksha.user_management_service.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

@Data
@Table(name = "auth_users")
@Entity
public class User {
  @Id
  @GeneratedValue(generator = "uuid")
  private UUID id;
  @NonNull
  private String username;
  @NonNull
  private String password;
  private List<String> roles;

  public User() {
  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
