package com.jeevanraksha.appointment_service.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Patient {
  private String firstName;
  private String lastName;
  private LocalDate dateOfBirth;
  private String gender;
  private String contactNumber;
  private String email;
}
