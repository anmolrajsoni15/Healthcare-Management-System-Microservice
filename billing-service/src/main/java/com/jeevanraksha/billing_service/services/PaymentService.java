package com.jeevanraksha.billing_service.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeevanraksha.billing_service.entity.Invoice;
import com.jeevanraksha.billing_service.entity.Payment;
import com.jeevanraksha.billing_service.repository.InvoiceRepository;
import com.jeevanraksha.billing_service.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PaymentService {

  @Autowired
  private RazorpayClient razorpayClient;

  @Autowired
  private InvoiceRepository invoiceRepository;

  @Autowired
  private PaymentRepository paymentRepository;

  public String createOrder(UUID invoiceId, BigDecimal amount) throws RazorpayException {
    Invoice invoice = invoiceRepository.findById(invoiceId)
        .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));

    JSONObject orderRequest = new JSONObject();
    orderRequest.put("amount", amount.multiply(BigDecimal.valueOf(100))); // Amount in paise
    orderRequest.put("currency", "INR");
    orderRequest.put("receipt", invoice.getInvoiceId().toString());

    Order order = razorpayClient.orders.create(orderRequest);

    return order.get("id");
  }

  public void capturePayment(String paymentId, BigDecimal amount) throws RazorpayException {
    JSONObject captureRequest = new JSONObject();
    captureRequest.put("amount", amount.multiply(BigDecimal.valueOf(100))); // Amount in paise

    razorpayClient.payments.capture(paymentId, captureRequest);
  }

  public Payment recordPayment(UUID invoiceId, String razorpayPaymentId, BigDecimal amount) {
    Invoice invoice = invoiceRepository.findById(invoiceId)
        .orElseThrow(() -> new EntityNotFoundException("Invoice not found"));

    Payment payment = new Payment();
    payment.setPaymentDate(LocalDateTime.now());
    payment.setAmount(amount);
    payment.setInvoice(invoice);
    payment.setInvoiceId(invoiceId);
    payment.setRazorpayId(razorpayPaymentId);
    paymentRepository.save(payment);

    BigDecimal newBalance = invoice.getBalanceDue().subtract(amount).abs();
    invoice.setBalanceDue(newBalance);

    if (newBalance.compareTo(BigDecimal.ZERO) == 0) {
      invoice.setStatus("PAID");
    } else {
      invoice.setStatus("PARTIALLY_PAID");
    }

    invoiceRepository.save(invoice);
    return payment;
  }
}
