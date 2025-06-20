package com.company_software.invoices_service.mapper;

import org.springframework.stereotype.Component;

import com.company_software.invoices_service.dto.InvoiceRequestDTO;
import com.company_software.invoices_service.dto.InvoiceResponseDTO;
import com.company_software.invoices_service.models.Invoice;
import com.company_software.invoices_service.models.Invoice.StateInvoice;

import java.time.LocalDate;

@Component
public class InvoiceMapper {

    public Invoice toEntity(InvoiceRequestDTO dto) {
        Invoice invoice = new Invoice();
        invoice.setOrderId(dto.getOrderId());
        invoice.setClientId(dto.getClientId());
        invoice.setDate(LocalDate.now());
        invoice.setTotal(dto.getTotal());
        invoice.setState(StateInvoice.PENDIENTE);
        return invoice;
    }

    public InvoiceResponseDTO toDTO(Invoice invoice) {
        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.setId(invoice.getId());
        dto.setOrderId(invoice.getOrderId());
        dto.setClientId(invoice.getClientId());
        dto.setDate(invoice.getDate());
        dto.setTotal(invoice.getTotal());
        dto.setState(invoice.getState());
        return dto;
    }
}
