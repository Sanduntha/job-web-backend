package com.example.backend.service;

import com.example.backend.dto.EmployerDto;

public interface EmployerService {
    EmployerDto createEmployer(EmployerDto employerDto) throws Exception;
    EmployerDto getEmployerByUserId(Long userId) throws Exception;

    EmployerDto getEmployerByEmail(String email);
}
