package com.company_software.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company_software.inventory_service.models.Inventory;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductId(Long productId);
}
