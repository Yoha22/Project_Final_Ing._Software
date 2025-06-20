package com.company_software.invoices_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company_software.invoices_service.models.Invoice;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByClientId(Long clientId);
}
