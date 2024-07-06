package com.jeevanraksha.notification_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Service;

import com.jeevanraksha.notification_service.dto.NotificationMessage;

@Service
public class NotificationReceiver {

    private final NotificationService notificationService;

    public NotificationReceiver(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListeners(value = { @RabbitListener(queues = "appointment.booked"), @RabbitListener(queues = "appointment.reminder"), @RabbitListener(queues = "payment.confirmation"), @RabbitListener(queues = "invoice.generated")})
    public void receiveMessage(NotificationMessage message) {
        notificationService.processNotification(message);
    }
}
