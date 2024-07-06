package com.jeevanraksha.appointment_service.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jeevanraksha.appointment_service.dto.Patient;

@FeignClient(name = "PATIENT-SERVICE", url="${patient.url}", configuration = FeignClientConfiguration.class)
public interface PatientDetailClient {
  @GetMapping("/patients/{patientId}/appointment")
  Patient getPatientDetailsForAppointment(@PathVariable UUID patientId);
}
