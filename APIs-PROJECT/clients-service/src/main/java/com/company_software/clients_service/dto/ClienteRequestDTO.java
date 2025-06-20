package com.company_software.clients_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClienteRequestDTO {
    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String password;

    private String address;

    private String number_phone;
}