package com.company_software.users_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company_software.users_service.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
