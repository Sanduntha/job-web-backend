package com.example.backend.service;

import com.example.backend.dto.ApplicationDto;

import java.util.List;

public interface ApplicationService {
    ApplicationDto apply(ApplicationDto applicationDto) throws Exception;
    List<ApplicationDto> getApplicationsByJobSeeker(Long jobSeekerId);
}
