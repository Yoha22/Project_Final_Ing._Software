package com.products_service.products_service.mapper;

import org.springframework.stereotype.Component;

import com.products_service.products_service.dto.ProductRequestDTO;
import com.products_service.products_service.dto.ProductResponseDTO;
import com.products_service.products_service.models.Product;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDTO dto) {
        return new Product(
                null,
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStock(),
                dto.getImageUrl()
        );
    }

    public ProductResponseDTO toDTO(Product entity) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setImageUrl(entity.getImageUrl());
        return dto;
    }
}
