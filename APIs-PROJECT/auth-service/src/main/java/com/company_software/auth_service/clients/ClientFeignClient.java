package com.company_software.auth_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.company_software.auth_service.dto.ClientRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "clients-service", url = "http://localhost:8080")
public interface ClientFeignClient {

    @PostMapping("/api/clients")
    void createClient(@RequestBody ClientRequest clientRequest);
}