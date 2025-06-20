package com.company_software.users_service.mapper;

import org.springframework.stereotype.Component;

import com.company_software.users_service.dto.UserRequestDTO;
import com.company_software.users_service.dto.UserResponseDTO;
import com.company_software.users_service.models.User;

@Component
public class UserMapper {

    public User toEntity(UserRequestDTO dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .position(dto.getPosition())
                .role(dto.getRole())
                .build();
    }

    public UserResponseDTO toDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPosition(user.getPosition());
        dto.setRole(user.getRole());
        return dto;
    }
}

