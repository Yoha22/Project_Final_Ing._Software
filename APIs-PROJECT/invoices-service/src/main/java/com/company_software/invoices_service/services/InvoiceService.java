package com.company_software.invoices_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.company_software.invoices_service.dto.InvoiceRequestDTO;
import com.company_software.invoices_service.dto.InvoiceResponseDTO;
import com.company_software.invoices_service.mapper.InvoiceMapper;
import com.company_software.invoices_service.models.Invoice;
import com.company_software.invoices_service.models.Invoice.StateInvoice;
import com.company_software.invoices_service.repository.InvoiceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final InvoiceMapper mapper;

    public InvoiceResponseDTO createInvoice(InvoiceRequestDTO dto) {
        Invoice invoice = mapper.toEntity(dto);
        return mapper.toDTO(invoiceRepository.save(invoice));
    }

    public List<InvoiceResponseDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<InvoiceResponseDTO> getInvoiceById(Long id) {
        return invoiceRepository.findById(id).map(mapper::toDTO);
    }

    public List<InvoiceResponseDTO> getInvoicesByClient(Long clientId) {
        return invoiceRepository.findByClientId(clientId).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<InvoiceResponseDTO> markAsPaid(Long id) {
        return invoiceRepository.findById(id).map(invoice -> {
            invoice.setState(StateInvoice.PAGADO);
            invoiceRepository.save(invoice);
            return mapper.toDTO(invoice);
        });
    }
}
