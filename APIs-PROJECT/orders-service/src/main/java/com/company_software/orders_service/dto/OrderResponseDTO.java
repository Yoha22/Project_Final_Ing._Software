package com.company_software.orders_service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class OrderResponseDTO {
    private Long id;
    private Long clientId;
    private LocalDate date;
    private BigDecimal total;
    private List<OrderItemResponseDTO> items;
}
