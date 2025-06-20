package com.products_service.products_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.products_service.products_service.dto.ProductRequestDTO;
import com.products_service.products_service.dto.ProductResponseDTO;
import com.products_service.products_service.mapper.ProductMapper;
import com.products_service.products_service.models.Product;
import com.products_service.products_service.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public ProductResponseDTO save(ProductRequestDTO dto) {
        Product product = mapper.toEntity(dto);
        return mapper.toDTO(productRepository.save(product));
    }

    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public Optional<ProductResponseDTO> findById(Long id) {
        return productRepository.findById(id).map(mapper::toDTO);
    }

    public boolean deleteById(Long id) {
        if (!productRepository.existsById(id))
            return false;
        productRepository.deleteById(id);
        return true;
    }
}
