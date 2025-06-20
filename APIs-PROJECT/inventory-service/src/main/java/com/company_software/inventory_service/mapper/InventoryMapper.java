package com.company_software.inventory_service.mapper;

import org.springframework.stereotype.Component;

import com.company_software.inventory_service.dto.InventoryResponseDTO;
import com.company_software.inventory_service.models.Inventory;

@Component
public class InventoryMapper {

    public InventoryResponseDTO toDTO(Inventory entity) {
        InventoryResponseDTO dto = new InventoryResponseDTO();
        dto.setId(entity.getId());
        dto.setProductId(entity.getProductId());
        dto.setQuantity(entity.getQuantity());
        return dto;
    }
}
