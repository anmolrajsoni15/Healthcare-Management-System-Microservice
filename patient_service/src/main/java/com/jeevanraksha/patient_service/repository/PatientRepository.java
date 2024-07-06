package com.jeevanraksha.patient_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeevanraksha.patient_service.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
  Patient findByPatientId(UUID patientId);
}
