package com.company_software.invoices_service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.company_software.invoices_service.models.Invoice.StateInvoice;

@Data
public class InvoiceResponseDTO {
    private Long id;
    private Long orderId;
    private Long clientId;
    private LocalDate date;
    private BigDecimal total;
    private StateInvoice state;
}
