package com.company_software.clients_service.mapper;

import com.company_software.clients_service.dto.ClienteRequestDTO;
import com.company_software.clients_service.dto.ClienteResponseDTO;
import com.company_software.clients_service.models.Client;

public class ClientMapper {

    public static ClienteResponseDTO toDto(Client client) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setEmail(client.getEmail());
        dto.setAddress(client.getAddress());
        dto.setNumber_phone(client.getNumber_phone());
        return dto;
    }

    public static Client toEntity(ClienteRequestDTO dto) {
        return new Client(null, dto.getName(), dto.getEmail(),
                dto.getPassword(), dto.getAddress(), dto.getNumber_phone());
    }
}
