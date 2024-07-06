package com.jeevanraksha.gateway.model;

import lombok.Data;

@Data
public class Patient {
  private String username;
  private String password;
  private String role;

  public Patient(String username, String password, String role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }
}
