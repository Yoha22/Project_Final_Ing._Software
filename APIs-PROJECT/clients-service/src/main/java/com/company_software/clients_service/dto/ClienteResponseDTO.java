package com.company_software.clients_service.dto;

import lombok.Data;

@Data
public class ClienteResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String number_phone;
}
