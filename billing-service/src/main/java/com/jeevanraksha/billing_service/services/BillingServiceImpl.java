package com.jeevanraksha.billing_service.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeevanraksha.billing_service.entity.Invoice;
import com.jeevanraksha.billing_service.entity.Payment;
import com.jeevanraksha.billing_service.repository.InvoiceRepository;
import com.jeevanraksha.billing_service.repository.PaymentRepository;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Optional<Invoice> getInvoiceById(UUID invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        invoice.setIssueDate(LocalDateTime.now());
        invoice.setDueDate(LocalDateTime.now().plusDays(30));
        invoice.setStatus("UNPAID");
        return invoiceRepository.save(invoice);
    }

    @Override
    public Optional<Invoice> updateInvoice(UUID invoiceId, Invoice updatedInvoice) {
        return invoiceRepository.findById(invoiceId)
            .map(existingInvoice -> {
                existingInvoice.setPatientId(updatedInvoice.getPatientId());
                existingInvoice.setAppointmentId(updatedInvoice.getAppointmentId());
                existingInvoice.setAmount(updatedInvoice.getAmount());
                existingInvoice.setBalanceDue(updatedInvoice.getBalanceDue());
                existingInvoice.setStatus(updatedInvoice.getStatus());
                return invoiceRepository.save(existingInvoice);
            });
    }

    @Override
    public void deleteInvoice(UUID invoiceId) {
        invoiceRepository.deleteById(invoiceId);
    }

    @Override
    public List<Payment> getPaymentsByInvoiceId(UUID invoiceId) {
        return paymentRepository.findByInvoiceId(invoiceId);
    }
}
