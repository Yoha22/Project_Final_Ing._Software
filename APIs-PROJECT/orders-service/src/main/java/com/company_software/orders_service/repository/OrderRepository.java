package com.company_software.orders_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company_software.orders_service.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
