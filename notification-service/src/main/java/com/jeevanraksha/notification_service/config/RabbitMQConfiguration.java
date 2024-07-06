package com.jeevanraksha.notification_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

  @Bean
  public Queue appointmentBookingQueue() {
    return new Queue("appointment.booked");
  }

  @Bean
  public Queue appointmentCancelledQueue() {
    return new Queue("appointment.cancelled");
  }

  @Bean
  public Queue appointmentRescheduledQueue() {
    return new Queue("appointment.rescheduled");
  }

  @Bean
  public Queue appointmentReminderQueue() {
    return new Queue("appointment.reminder");
  }

  @Bean
  public Queue paymentConfirmationQueue() {
    return new Queue("payment.confirmation");
  }

  @Bean
  public Queue invoiceGeneratedQueue() {
    return new Queue("invoice.generated");
  }


  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jsonMessageConverter());
    return template;
  }
}
