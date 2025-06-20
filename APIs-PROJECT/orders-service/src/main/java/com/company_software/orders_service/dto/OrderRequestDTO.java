package com.company_software.orders_service.dto;

import java.util.List;
import lombok.Data;

@Data
public class OrderRequestDTO {
    private Long clientId;
    private List<OrderItemRequestDTO> items;
}
