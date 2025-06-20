package com.company_software.inventory_service.dto;

import lombok.Data;

@Data
public class InventoryRequestDTO {
    private Long productId;
    private int quantity;
}
