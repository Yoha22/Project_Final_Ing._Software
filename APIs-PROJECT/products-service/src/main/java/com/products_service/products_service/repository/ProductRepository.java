package com.products_service.products_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.products_service.products_service.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}