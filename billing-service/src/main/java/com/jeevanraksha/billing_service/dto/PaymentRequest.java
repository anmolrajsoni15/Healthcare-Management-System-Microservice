package com.jeevanraksha.billing_service.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentRequest {
  private String razorpayPaymentId;
  private BigDecimal amount;
}
