package com.jeevanraksha.billing_service.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentId;
    private UUID invoiceId;
    private LocalDateTime paymentDate;
    private BigDecimal amount;
    private String razorpayId;

    @ManyToOne
    @JoinColumn(name = "invoiceRefId", referencedColumnName = "invoiceId")
    @JsonBackReference
    private Invoice invoice;

    public Payment() {
    }

}
