package com.jeevanraksha.billing_service.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID invoiceId;
    private UUID patientId;
    private UUID appointmentId;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private BigDecimal amount;
    private BigDecimal balanceDue;
    private String status; // PAID, UNPAID, PARTIALLY_PAID

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Payment> payments;

    public Invoice() {
    }

    public Invoice(UUID invoiceId, UUID patientId, UUID appointmentId, LocalDateTime issueDate, LocalDateTime dueDate,
        BigDecimal amount, BigDecimal balanceDue, String status, List<Payment> payments) {
      this.invoiceId = invoiceId;
      this.patientId = patientId;
      this.appointmentId = appointmentId;
      this.issueDate = issueDate;
      this.dueDate = dueDate;
      this.amount = amount;
      this.balanceDue = balanceDue;
      this.status = status;
      this.payments = payments;
    }

}
