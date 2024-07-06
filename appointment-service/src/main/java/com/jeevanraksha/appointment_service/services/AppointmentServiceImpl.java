package com.jeevanraksha.appointment_service.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jeevanraksha.appointment_service.client.DoctorAvailabilityClient;
import com.jeevanraksha.appointment_service.dto.DoctorAvailability;
import com.jeevanraksha.appointment_service.entity.Appointment;
import com.jeevanraksha.appointment_service.repository.AppointmentRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorAvailabilityClient doctorAvailabilityClient;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
            DoctorAvailabilityClient doctorAvailabilityClient) {
        this.appointmentRepository = appointmentRepository;
        this.doctorAvailabilityClient = doctorAvailabilityClient;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public Optional<Appointment> getAppointmentById(UUID appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        List<DoctorAvailability> availabilities = doctorAvailabilityClient
                .getAvailabilitiesByDoctorId(appointment.getDoctorId());
        if (availabilities.stream().anyMatch(a -> !appointment.getAppointmentDate().isBefore(a.getAvailableFrom()) &&
                !appointment.getAppointmentDate().isAfter(a.getAvailableTo()))) {
            return appointmentRepository.save(appointment);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Doctor not available at the requested time");
        }
    }

    @Override
    public Optional<Appointment> updateAppointment(UUID appointmentId, Appointment updatedAppointment) {
        return appointmentRepository.findById(appointmentId)
                .map(existingAppointment -> {
                    existingAppointment.setPatientId(updatedAppointment.getPatientId());
                    existingAppointment.setDoctorId(updatedAppointment.getDoctorId());
                    existingAppointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
                    existingAppointment.setStatus(updatedAppointment.getStatus());
                    existingAppointment.setNotes(updatedAppointment.getNotes());
                    return appointmentRepository.save(existingAppointment);
                });
    }

    @Override
    public void deleteAppointment(UUID appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    public List<DoctorAvailability> checkDoctorAvailability(UUID doctorId, LocalDate date) {
        return doctorAvailabilityClient.getAvailabilitiesByDoctorId(doctorId);
    }
}
