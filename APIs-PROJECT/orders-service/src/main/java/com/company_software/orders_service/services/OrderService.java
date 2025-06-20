package com.company_software.orders_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.company_software.orders_service.dto.OrderRequestDTO;
import com.company_software.orders_service.dto.OrderResponseDTO;
import com.company_software.orders_service.mapper.OrderMapper;
import com.company_software.orders_service.models.Order;
import com.company_software.orders_service.repository.OrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        Order order = orderMapper.toEntity(dto);
        return orderMapper.toDTO(orderRepository.save(order));
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrderResponseDTO> getOrderById(Long id) {
        return orderRepository.findById(id).map(orderMapper::toDTO);
    }

    public boolean deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) return false;
        orderRepository.deleteById(id);
        return true;
    }
}
