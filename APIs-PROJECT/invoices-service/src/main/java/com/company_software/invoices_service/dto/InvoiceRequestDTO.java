package com.company_software.invoices_service.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class InvoiceRequestDTO {
    private Long orderId;
    private Long clientId;
    private BigDecimal total;
}
