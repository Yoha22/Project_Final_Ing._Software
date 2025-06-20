package com.company_software.clients_service.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.company_software.clients_service.dto.ClienteRequestDTO;
import com.company_software.clients_service.dto.ClienteResponseDTO;
import com.company_software.clients_service.mapper.ClientMapper;
import com.company_software.clients_service.models.Client;
import com.company_software.clients_service.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    public ClienteResponseDTO storeClient(ClienteRequestDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        Client client = ClientMapper.toEntity(dto);
        Client saved = repository.save(client);
        return ClientMapper.toDto(saved);
    }

    public ClienteResponseDTO obtenerCliente(Long id) {
        Client cliente = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No existe el cliente"));
        return ClientMapper.toDto(cliente);
    }

    public List<ClienteResponseDTO> listarClientes() {
        return repository.findAll().stream()
                .map(ClientMapper::toDto)
                .toList();
    }
}