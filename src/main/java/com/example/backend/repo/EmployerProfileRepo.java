package com.example.backend.repo;

import com.example.backend.entity.EmployerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployerProfileRepo extends JpaRepository<EmployerProfile, Long> {
    Optional<EmployerProfile> findByUserId(Long userId);
}
