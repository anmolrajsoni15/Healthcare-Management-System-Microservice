package com.jeevanraksha.user_management_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeevanraksha.user_management_service.model.Patient;
import com.jeevanraksha.user_management_service.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService userService;
  
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/create-patient-user")
  public boolean createPatientUser(@RequestBody Patient patient) {
    System.out.println("Creating patient user: " + patient);
    return userService.createNewPatientUser(patient);
  }
}
