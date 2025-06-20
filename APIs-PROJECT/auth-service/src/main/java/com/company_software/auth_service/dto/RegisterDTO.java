package com.company_software.auth_service.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
}
