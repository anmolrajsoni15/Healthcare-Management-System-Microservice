package com.jeevanraksha.billing_service.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeevanraksha.billing_service.dto.PaymentRequest;
import com.jeevanraksha.billing_service.entity.InsuranceClaim;
import com.jeevanraksha.billing_service.entity.Invoice;
import com.jeevanraksha.billing_service.entity.Payment;
import com.jeevanraksha.billing_service.services.BillingService;
import com.jeevanraksha.billing_service.services.InsuranceClaimService;
import com.jeevanraksha.billing_service.services.PaymentService;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private InsuranceClaimService insuranceClaimService;

    @GetMapping("/invoices")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT')")
    public List<Invoice> getAllInvoices() {
        return billingService.getAllInvoices();
    }

    @GetMapping("/invoices/{invoiceId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT')")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable UUID invoiceId) {
        return billingService.getInvoiceById(invoiceId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/invoices")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return billingService.createInvoice(invoice);
    }

    @PutMapping("/invoices/{invoiceId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT')")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable UUID invoiceId, @RequestBody Invoice invoice) {
        return billingService.updateInvoice(invoiceId, invoice)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/invoices/{invoiceId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteInvoice(@PathVariable UUID invoiceId) {
        billingService.deleteInvoice(invoiceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/invoices/{invoiceId}/payments")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT')")
    public List<Payment> getPaymentsByInvoiceId(@PathVariable UUID invoiceId) {
        return billingService.getPaymentsByInvoiceId(invoiceId);
    }

    @PostMapping("/invoices/{invoiceId}/create-order")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT') or hasRole('PATIENT')")
    public ResponseEntity<String> createOrder(@PathVariable UUID invoiceId, @RequestParam BigDecimal amount) throws RazorpayException {
        String orderId = paymentService.createOrder(invoiceId, amount);
        return ResponseEntity.ok(orderId);
    }

    @PostMapping("/invoices/{invoiceId}/payments")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT') or hasRole('PATIENT')")
    public Payment recordPayment(@PathVariable UUID invoiceId, @RequestBody PaymentRequest paymentRequest) {
        // paymentService.capturePayment(paymentRequest.getRazorpayPaymentId(), paymentRequest.getAmount());
        return paymentService.recordPayment(invoiceId, paymentRequest.getRazorpayPaymentId(), paymentRequest.getAmount());
    }

    @GetMapping("/insurance-claims")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT')")
    public List<InsuranceClaim> getAllClaims() {
        return insuranceClaimService.getAllClaims();
    }

    @GetMapping("/insurance-claims/{claimId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT')")
    public ResponseEntity<InsuranceClaim> getClaimById(@PathVariable UUID claimId) {
        return insuranceClaimService.getClaimById(claimId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/insurance-claims")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT')")
    public InsuranceClaim createClaim(@RequestBody InsuranceClaim claim) {
        return insuranceClaimService.createClaim(claim);
    }

    @PutMapping("/insurance-claims/{claimId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('ACCOUNTANT')")
    public ResponseEntity<InsuranceClaim> updateClaim(@PathVariable UUID claimId, @RequestBody InsuranceClaim claim) {
        return insuranceClaimService.updateClaim(claimId, claim)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/insurance-claims/{claimId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteClaim(@PathVariable UUID claimId) {
        insuranceClaimService.deleteClaim(claimId);
        return ResponseEntity.noContent().build();
    }
}

