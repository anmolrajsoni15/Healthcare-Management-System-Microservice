package com.jeevanraksha.appointment_service.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.jeevanraksha.appointment_service.dto.DoctorAvailability;
import com.jeevanraksha.appointment_service.entity.Appointment;

public interface AppointmentService {
  List<Appointment> getAllAppointments();
  Optional<Appointment> getAppointmentById(UUID appointmentId);
  Appointment createAppointment(Appointment appointment);
  Optional<Appointment> updateAppointment(UUID appointmentId, Appointment appointment);
  void deleteAppointment(UUID appointmentId);
  List<DoctorAvailability> checkDoctorAvailability(UUID doctorId, LocalDate date);
}

