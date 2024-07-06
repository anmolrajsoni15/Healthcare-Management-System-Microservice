package com.jeevanraksha.appointment_service.messaging;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.jeevanraksha.appointment_service.dto.NotificationMessage;
import com.jeevanraksha.appointment_service.dto.Patient;

@Service
public class MessageProducer {
  private final RabbitTemplate rabbitTemplate;

  public MessageProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendMessage(Patient patient, LocalDateTime appointmentTime) {
    NotificationMessage notificationMessage = new NotificationMessage();
    LocalDateTime time = appointmentTime;

    Object payload = new Object() {
      public String name = patient.getFirstName() + " " + patient.getLastName();
      public String email = patient.getEmail();
      public String phone = patient.getContactNumber();
      public LocalDate dob = patient.getDateOfBirth();
      public String gender = patient.getGender();
      public LocalDateTime appointmentTime = time;
    };

    notificationMessage.setType("APPOINTMENT_BOOKED");
    notificationMessage.setPayload(payload);

    rabbitTemplate.convertAndSend("appointment.booked", notificationMessage);
  }
}
