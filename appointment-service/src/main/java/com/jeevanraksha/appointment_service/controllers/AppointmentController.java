package com.jeevanraksha.appointment_service.controllers;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jeevanraksha.appointment_service.client.PatientDetailClient;
import com.jeevanraksha.appointment_service.dto.DoctorAvailability;
import com.jeevanraksha.appointment_service.dto.Patient;
import com.jeevanraksha.appointment_service.entity.Appointment;
import com.jeevanraksha.appointment_service.messaging.MessageProducer;
import com.jeevanraksha.appointment_service.services.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientDetailClient patientDetailClient;
    private MessageProducer messageProducer;

    public AppointmentController(AppointmentService appointmentService, PatientDetailClient patientDetailClient, MessageProducer messageProducer) {
        this.appointmentService = appointmentService;
        this.patientDetailClient = patientDetailClient;
        this.messageProducer = messageProducer;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('NURSE')")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{appointmentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('NURSE') or (hasRole('PATIENT') and @appointmentSecurity.isOwner(authentication, #appointmentId))")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable UUID appointmentId) {
        return appointmentService.getAppointmentById(appointmentId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        // Check doctor availability before creating an appointment
        List<DoctorAvailability> availabilities = appointmentService.checkDoctorAvailability(appointment.getDoctorId(), appointment.getAppointmentDate().toLocalDate());
        if (availabilities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor not available at the requested time");
        }

        Patient patient = patientDetailClient.getPatientDetailsForAppointment(appointment.getPatientId());
        messageProducer.sendMessage(patient, appointment.getAppointmentDate());

        return appointmentService.createAppointment(appointment);
    }

    @PutMapping("/{appointmentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable UUID appointmentId, @RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(appointmentId, appointment)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{appointmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAppointment(@PathVariable UUID appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }
}
