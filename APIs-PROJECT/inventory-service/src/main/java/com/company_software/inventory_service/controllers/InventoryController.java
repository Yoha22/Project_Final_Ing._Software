package com.company_software.inventory_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company_software.inventory_service.dto.InventoryRequestDTO;
import com.company_software.inventory_service.dto.InventoryResponseDTO;
import com.company_software.inventory_service.services.InventoryService;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponseDTO> getByProductId(@PathVariable Long productId) {
        return service.getByProductId(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<InventoryResponseDTO> addStock(@RequestBody InventoryRequestDTO dto) {
        return ResponseEntity.ok(service.addStock(dto));
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> removeStock(@RequestBody InventoryRequestDTO dto) {
        return service.reduceStock(dto)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.badRequest().build();
    }
}
