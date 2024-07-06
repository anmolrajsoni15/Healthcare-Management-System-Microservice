package com.jeevanraksha.patient_service.dto;

import lombok.Data;

@Data
public class PatientUser {
  private String username;
  private String password;
  private String role;

  public PatientUser() {
  }

  public PatientUser(String username, String password, String role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }
}
