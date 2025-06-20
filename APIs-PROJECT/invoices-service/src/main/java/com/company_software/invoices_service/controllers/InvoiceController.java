package com.company_software.invoices_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company_software.invoices_service.dto.InvoiceRequestDTO;
import com.company_software.invoices_service.dto.InvoiceResponseDTO;
import com.company_software.invoices_service.services.InvoiceService;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> create(@RequestBody InvoiceRequestDTO dto) {
        return ResponseEntity.ok(invoiceService.createInvoice(dto));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponseDTO>> getAll() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDTO> getById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<InvoiceResponseDTO>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(invoiceService.getInvoicesByClient(clientId));
    }

    @PatchMapping("/{id}/pagado")
    public ResponseEntity<InvoiceResponseDTO> markAsPaid(@PathVariable Long id) {
        return invoiceService.markAsPaid(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
