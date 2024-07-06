package com.jeevanraksha.patient_service.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeevanraksha.patient_service.client.CreateUserClient;
import com.jeevanraksha.patient_service.dto.PatientUser;
import com.jeevanraksha.patient_service.entity.Patient;
import com.jeevanraksha.patient_service.services.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientController {
  @Autowired
  private PatientService patientService;

  @Autowired
  private CreateUserClient createUserClient;

  @GetMapping("/info")
    public ResponseEntity<?> getPatientInfo(@RequestHeader(value = "X-User-Id", required = false) String userId,
                                            @RequestHeader(value = "Authorization", required = false) String authorization) {
        // Log the received headers for debugging purposes
        System.out.println("Received Authorization Header: " + authorization);
        System.out.println("Received User ID: " + userId);

        if (userId == null || authorization == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized hai bhai!");
        }

        // Use the userId to fetch and return patient information
        return ResponseEntity.ok("Patient info for user: " + userId);
    }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('NURSE')")
  public List<Patient> getAllPatients() {
    return patientService.getAllPatients();
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> addPatient(@RequestBody Patient patient) {
    String username = patient.getEmail().split("@")[0];
    String password = patient.getContactNumber().toString();
    String role = "PATIENT";

    PatientUser patientUser = new PatientUser(username, password, role);

    System.out.println("Patient User: " + patientUser);
    boolean userCreated = createUserClient.createPatientUser(patientUser);
    patientService.addPatient(patient);

    if (userCreated) {
      return ResponseEntity.ok("Patient added successfully");
    } else {
      return ResponseEntity.ok("Patient added successfully, but user creation failed. Add user manually.");
    }
    // return ResponseEntity.ok("Patient added successfully");
  }

  @GetMapping("/{patientId}")
  @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('NURSE') or (hasRole('PATIENT') and @patientSecurity.isSelf(authentication, #patientId))")
  public ResponseEntity<Patient> getPatientById(@PathVariable UUID patientId) {
    return patientService.getPatientById(patientId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/{patientId}/appointment")
  @PreAuthorize("hasRole('DOCTOR') or hasRole('NURSE') or hasRole('ADMIN') ")
  public ResponseEntity<?> getPatientDetailsForAppointment(@PathVariable UUID patientId) {
    try {
      return ResponseEntity.ok(patientService.getPatientDetailsForAppointment(patientId));
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{patientId}")
  @PreAuthorize("hasRole('ADMIN')")
  public Optional<Patient> updatePatientAsAdmin(@PathVariable UUID patientId, @RequestBody Patient updatedPatient) {
    return patientService.updatePatientAsAdmin(patientId, updatedPatient);
  }

  @PutMapping("/{patientId}/medical")
  @PreAuthorize("hasRole('DOCTOR')")
  public Optional<Patient> updatePatientAsDoctor(@PathVariable UUID patientId, @RequestBody Patient updatedPatient) {
    return patientService.updatePatientAsDoctor(patientId, updatedPatient);
  }

  @PutMapping("/{patientId}/contact")
  @PreAuthorize("hasRole('NURSE')")
  public Optional<Patient> updatePatientAsNurse(@PathVariable UUID patientId, @RequestBody Patient updatedPatient) {
    return patientService.updatePatientAsNurse(patientId, updatedPatient);
  }

  @PutMapping("/{patientId}/self")
  @PreAuthorize("hasRole('PATIENT')")
  public Optional<Patient> updatePatientAsPatient(@PathVariable UUID patientId, @RequestBody Patient updatedPatient) {
    return patientService.updatePatientAsPatient(patientId, updatedPatient);
  }

  @DeleteMapping("/{patientId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deletePatient(@PathVariable UUID patientId) {
    patientService.deletePatient(patientId);
    return ResponseEntity.noContent().build();
  }
}
