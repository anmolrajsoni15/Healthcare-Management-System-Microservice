package com.jeevanraksha.patient_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.jeevanraksha.patient_service.dto.PatientUser;

@FeignClient(name = "user-management-service", url="${usermanagement.url}", configuration = FeignClientConfiguration.class)
public interface CreateUserClient {
  @PostMapping("/users/create-patient-user")
  public boolean createPatientUser(@RequestBody PatientUser patient);
}
