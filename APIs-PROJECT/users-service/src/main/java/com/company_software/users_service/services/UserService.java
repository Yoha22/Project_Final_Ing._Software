package com.company_software.users_service.services;

import org.springframework.stereotype.Service;

import com.company_software.users_service.dto.UserRequestDTO;
import com.company_software.users_service.dto.UserResponseDTO;
import com.company_software.users_service.mapper.UserMapper;
import com.company_software.users_service.models.User;
import com.company_software.users_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserResponseDTO createUser(UserRequestDTO dto) {
        User user = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(user));
    }

    public List<UserResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserResponseDTO> getById(Long id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public boolean deleteById(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public Optional<UserResponseDTO> updateUser(Long id, UserRequestDTO dto) {
        return repository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setEmail(dto.getEmail());
            existing.setPosition(dto.getPosition());
            existing.setRole(dto.getRole());
            return mapper.toDTO(repository.save(existing));
        });
    }
}
