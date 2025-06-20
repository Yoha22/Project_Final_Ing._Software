package com.company_software.orders_service.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemResponseDTO {
    private Long productId;
    private int quantity;
    private BigDecimal priceUnit;
    private BigDecimal subTotal;
}
