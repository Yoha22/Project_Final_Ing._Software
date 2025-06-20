package com.company_software.auth_service.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.company_software.auth_service.clients.ClientFeignClient;
import com.company_software.auth_service.dto.AuthRequestDTO;
import com.company_software.auth_service.dto.AuthResponseDTO;
import com.company_software.auth_service.dto.ClientRequest;
import com.company_software.auth_service.dto.RegisterDTO;
import com.company_software.auth_service.mapper.UserMapper;
import com.company_software.auth_service.models.JwtService;
import com.company_software.auth_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserMapper userMapper;
    private final ClientFeignClient clientsFeignClient;

    public AuthResponseDTO register(RegisterDTO dto) {
        var user = userMapper.toEntity(dto);
        userRepository.save(user);
        if ("CLIENT".equalsIgnoreCase(user.getRole().name())) {
            ClientRequest clientDto = new ClientRequest(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress()
            );
            clientsFeignClient.createClient(clientDto);
        }
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponseDTO(token, userMapper.toUserDTO(user));
    }

    public AuthResponseDTO login(AuthRequestDTO dto) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        var user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow();
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponseDTO(token, userMapper.toUserDTO(user));
    }
}
