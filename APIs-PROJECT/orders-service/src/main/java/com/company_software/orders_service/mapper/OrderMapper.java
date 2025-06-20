package com.company_software.orders_service.mapper;

import org.springframework.stereotype.Component;

import com.company_software.orders_service.dto.OrderItemResponseDTO;
import com.company_software.orders_service.dto.OrderRequestDTO;
import com.company_software.orders_service.dto.OrderResponseDTO;
import com.company_software.orders_service.models.Order;
import com.company_software.orders_service.models.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequestDTO dto) {
        Order order = new Order();
        order.setClientId(dto.getClientId());
        order.setDate(LocalDate.now());

        List<OrderItem> items = dto.getItems().stream()
                .map(itemDto -> {
                    OrderItem item = new OrderItem();
                    item.setProductId(itemDto.getProductId());
                    item.setQuantity(itemDto.getQuantity());
                    item.setPriceUnit(itemDto.getPriceUnit());
                    item.setSubTotal(itemDto.getPriceUnit().multiply(BigDecimal.valueOf(itemDto.getQuantity())));
                    item.setOrder(order);
                    return item;
                }).collect(Collectors.toList());

        order.setItems(items);
        order.setTotal(items.stream()
                .map(OrderItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        return order;
    }

    public OrderResponseDTO toDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setClientId(order.getClientId());
        dto.setDate(order.getDate());
        dto.setTotal(order.getTotal());

        List<OrderItemResponseDTO> itemDtos = order.getItems().stream()
                .map(item -> {
                    OrderItemResponseDTO i = new OrderItemResponseDTO();
                    i.setProductId(item.getProductId());
                    i.setQuantity(item.getQuantity());
                    i.setPriceUnit(item.getPriceUnit());
                    i.setSubTotal(item.getSubTotal());
                    return i;
                }).collect(Collectors.toList());

        dto.setItems(itemDtos);
        return dto;
    }
}
