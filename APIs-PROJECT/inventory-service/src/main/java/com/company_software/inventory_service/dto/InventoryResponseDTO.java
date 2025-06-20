package com.company_software.inventory_service.dto;

import lombok.Data;

@Data
public class InventoryResponseDTO {
    private Long id;
    private Long productId;
    private int quantity;
}
