package com.company_software.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
}