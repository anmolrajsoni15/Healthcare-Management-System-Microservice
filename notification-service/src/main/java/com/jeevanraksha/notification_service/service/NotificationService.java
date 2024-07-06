package com.jeevanraksha.notification_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeevanraksha.notification_service.dto.NotificationMessage;

import jakarta.mail.MessagingException;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final EmailService emailService;
    private final SmsService smsService;
    private final TemplateService templateService;

    // @Autowired
    public NotificationService(EmailService emailService, SmsService smsService, TemplateService templateService) {
        this.emailService = emailService;
        this.smsService = smsService;
        this.templateService = templateService;
        // this.rabbitTemplate = rabbitTemplate;
    }

    // public void initiateNotification(String method) {

    // switch(method){
    // case "APPOINTMENT_BOOKED":
    // NotificationMessage bookingMessage = (NotificationMessage)
    // rabbitTemplate.receiveAndConvert("appointment.booked");
    // processNotification(bookingMessage);
    // break;
    // case "APPOINTMENT_REMINDER":
    // NotificationMessage reminderMessage = (NotificationMessage)
    // rabbitTemplate.receiveAndConvert("appointment.reminder");
    // processNotification(reminderMessage);
    // break;
    // case "PAYMENT_CONFIRMATION":
    // NotificationMessage payConfirmation = (NotificationMessage)
    // rabbitTemplate.receiveAndConvert("payment.confirmation");
    // processNotification(payConfirmation);
    // break;
    // case "INVOICE_GENERATED":
    // NotificationMessage invoiceGenerated = (NotificationMessage)
    // rabbitTemplate.receiveAndConvert("invoice.generated");
    // processNotification(invoiceGenerated);
    // break;
    // default:
    // // Handle unknown notification type
    // break;
    // }
    // }

    public void processNotification(NotificationMessage message) {
        try {
            String notificationType = message.getType();
            Object payload = message.getPayload();

            switch (notificationType) {
                case "APPOINTMENT_BOOKED":
                    handleAppointmentBooked(payload);
                    break;
                case "APPOINTMENT_REMINDER":
                    handleAppointmentReminder(payload);
                    break;
                case "PAYMENT_CONFIRMATION":
                    handlePaymentConfirmation(payload);
                    break;
                case "INVOICE_GENERATED":
                    handleInvoiceGenerated(payload);
                    break;
                default:
                    // Handle unknown notification type
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleAppointmentBooked(Object payload) throws IOException, GeneralSecurityException, MessagingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(payload);
            JsonNode rootNode = mapper.readTree(jsonString);

            String name = rootNode.get("name").asText();
            String email = rootNode.get("email").asText();
            String phone = rootNode.get("phone").asText();

            // Parse the dob field
            JsonNode dobNode = rootNode.get("dob");
            String dob = dobNode.get(0).asInt() + "-" + dobNode.get(1).asInt() + "-" + dobNode.get(2).asInt();

            // Parse the appointmentTime field
            JsonNode appointmentTimeNode = rootNode.get("appointmentTime");
            String appointmentTime = appointmentTimeNode.get(0).asInt() + "-" + appointmentTimeNode.get(1).asInt() + "-"
                    + appointmentTimeNode.get(2).asInt() + " " + appointmentTimeNode.get(3).asInt() + ":"
                    + appointmentTimeNode.get(4).asInt() + ":" + appointmentTimeNode.get(5).asInt();

            String subject = "Appointment Booked";
            String emailData = "Hello " + name
                    + ",\n\nYour appointment has been booked successfully. Please find the details below:\n\n"
                    + "Email: " + email + "\nPhone: " + phone + "\nDate of Birth: " + dob + "\nAppointment Time: "
                    + appointmentTime + "\n\n"
                    + "Thank you for choosing our service.\n\nRegards,\nJeevan Raksha Hospital";
            String smsData = "Hello " + name + ", Your appointment has been booked successfully. Appointment Time: "
                    + appointmentTime;

            String emailContent = templateService.getTemplate(subject, emailData);
            String smsContent = templateService.getTemplate(subject, smsData);

            emailService.sendEmail(email, subject, emailContent);
            // smsService.sendSms(phone, smsContent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void handleAppointmentReminder(Object payload) throws IOException, GeneralSecurityException, MessagingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(payload);
            String emailContent = templateService.getTemplate("appointment_reminder", jsonString);
            String smsContent = templateService.getTemplate("appointment_reminder_sms", jsonString);

            emailService.sendEmail("recipient@example.com", "Appointment Reminder", emailContent);
            smsService.sendSms("recipientPhoneNumber", smsContent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void handlePaymentConfirmation(Object payload) throws IOException, GeneralSecurityException, MessagingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(payload);
            String emailContent = templateService.getTemplate("payment_confirmation", jsonString);
            String smsContent = templateService.getTemplate("payment_confirmation_sms", jsonString);

            emailService.sendEmail("recipient@example.com", "Payment Confirmation", emailContent);
            smsService.sendSms("recipientPhoneNumber", smsContent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void handleInvoiceGenerated(Object payload) throws IOException, GeneralSecurityException, MessagingException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(payload);
            String emailContent = templateService.getTemplate("invoice_generated", jsonString);
            String smsContent = templateService.getTemplate("invoice_generated_sms", jsonString);

            emailService.sendEmail("recipient@example.com", "Invoice Generated", emailContent);
            smsService.sendSms("recipientPhoneNumber", smsContent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // private void handlePrescriptionReady(Object payload) {
    // try {
    // ObjectMapper mapper = new ObjectMapper();
    // String jsonString = mapper.writeValueAsString(payload);
    // String emailContent = templateService.getTemplate("prescription_ready",
    // jsonString);
    // String smsContent = templateService.getTemplate("prescription_ready_sms",
    // jsonString);

    // emailService.sendEmail("recipient@example.com", "Prescription Ready",
    // emailContent);
    // smsService.sendSms("recipientPhoneNumber", smsContent);
    // } catch (JsonProcessingException e) {
    // e.printStackTrace();
    // }
    // }
}
