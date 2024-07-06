package com.jeevanraksha.appointment_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeevanraksha.appointment_service.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
}
