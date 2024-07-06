package com.jeevanraksha.patient_service.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeevanraksha.patient_service.dto.PatientAppointmentDTO;
import com.jeevanraksha.patient_service.entity.Patient;
import com.jeevanraksha.patient_service.repository.PatientRepository;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(UUID patientId) {
        return patientRepository.findById(patientId);
    }

    public PatientAppointmentDTO getPatientDetailsForAppointment(UUID patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if(patient.isPresent()){
            PatientAppointmentDTO patientAppointmentDTO = new PatientAppointmentDTO();
            patientAppointmentDTO.setFirstName(patient.get().getFirstName());
            patientAppointmentDTO.setLastName(patient.get().getLastName());
            patientAppointmentDTO.setGender(patient.get().getGender());
            patientAppointmentDTO.setDateOfBirth(patient.get().getDateOfBirth());
            patientAppointmentDTO.setContactNumber(patient.get().getContactNumber());
            patientAppointmentDTO.setEmail(patient.get().getEmail());
            return patientAppointmentDTO;
        }
        return null;
    }

    public void addPatient(Patient patient) {
        patientRepository.save(patient);
    }

    public Optional<Patient> updatePatientAsAdmin(UUID patientId, Patient updatedPatient) {
      return patientRepository.findById(patientId).map(patient -> {
          patient.setFirstName(updatedPatient.getFirstName());
          patient.setLastName(updatedPatient.getLastName());
          patient.setGender(updatedPatient.getGender());
          patient.setDateOfBirth(updatedPatient.getDateOfBirth());
          patient.setAddress(updatedPatient.getAddress());
          patient.setEmergencyContact(updatedPatient.getEmergencyContact());
          patient.setMedicalHistory(updatedPatient.getMedicalHistory());
          patient.setCurrentMedications(updatedPatient.getCurrentMedications());
          patient.setAllergies(updatedPatient.getAllergies());
          patient.setInsuranceDetails(updatedPatient.getInsuranceDetails());
          return patientRepository.save(patient);
      });
  }

  public Optional<Patient> updatePatientAsDoctor(UUID patientId, Patient updatedPatient) {
      return patientRepository.findById(patientId).map(patient -> {
          patient.setMedicalHistory(updatedPatient.getMedicalHistory());
          patient.setCurrentMedications(updatedPatient.getCurrentMedications());
          patient.setAllergies(updatedPatient.getAllergies());
          return patientRepository.save(patient);
      });
  }

  public Optional<Patient> updatePatientAsNurse(UUID patientId, Patient updatedPatient) {
      return patientRepository.findById(patientId).map(patient -> {
          patient.setAddress(updatedPatient.getAddress());
          patient.setEmergencyContact(updatedPatient.getEmergencyContact());
          patient.setInsuranceDetails(updatedPatient.getInsuranceDetails());
          return patientRepository.save(patient);
      });
  }

  public Optional<Patient> updatePatientAsPatient(UUID patientId, Patient updatedPatient) {
      return patientRepository.findById(patientId).map(patient -> {
          patient.setAddress(updatedPatient.getAddress());
          patient.setEmergencyContact(updatedPatient.getEmergencyContact());
          return patientRepository.save(patient);
      });
  }

    public void deletePatient(UUID patientId) {
        patientRepository.deleteById(patientId);
    }
}
