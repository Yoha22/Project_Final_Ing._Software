package com.company_software.inventory_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.company_software.inventory_service.dto.InventoryRequestDTO;
import com.company_software.inventory_service.dto.InventoryResponseDTO;
import com.company_software.inventory_service.mapper.InventoryMapper;
import com.company_software.inventory_service.models.Inventory;
import com.company_software.inventory_service.repository.InventoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository repository;
    private final InventoryMapper mapper;

    public List<InventoryResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<InventoryResponseDTO> getByProductId(Long productId) {
        return repository.findByProductId(productId).map(mapper::toDTO);
    }

    public InventoryResponseDTO addStock(InventoryRequestDTO dto) {
        Inventory inventory = repository.findByProductId(dto.getProductId())
                .orElse(new Inventory(null, dto.getProductId(), 0));
        inventory.setQuantity(inventory.getQuantity() + dto.getQuantity());
        return mapper.toDTO(repository.save(inventory));
    }

    public boolean reduceStock(InventoryRequestDTO dto) {
        Optional<Inventory> optional = repository.findByProductId(dto.getProductId());
        if (optional.isEmpty()) return false;

        Inventory inventory = optional.get();
        if (inventory.getQuantity() < dto.getQuantity()) return false;

        inventory.setQuantity(inventory.getQuantity() - dto.getQuantity());
        repository.save(inventory);
        return true;
    }
}
