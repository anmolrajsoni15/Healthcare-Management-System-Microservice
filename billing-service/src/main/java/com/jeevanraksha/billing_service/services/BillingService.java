package com.jeevanraksha.billing_service.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.jeevanraksha.billing_service.entity.Invoice;
import com.jeevanraksha.billing_service.entity.Payment;

public interface BillingService {
  List<Invoice> getAllInvoices();
    Optional<Invoice> getInvoiceById(UUID invoiceId);
    Invoice createInvoice(Invoice invoice);
    Optional<Invoice> updateInvoice(UUID invoiceId, Invoice invoice);
    void deleteInvoice(UUID invoiceId);

    List<Payment> getPaymentsByInvoiceId(UUID invoiceId);
}
