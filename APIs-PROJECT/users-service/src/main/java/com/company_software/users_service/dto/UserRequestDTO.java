package com.company_software.users_service.dto;

import com.company_software.users_service.models.Role;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String position;
    private Role role;
}
