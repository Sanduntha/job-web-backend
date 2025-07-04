package com.example.backend.service;

import com.example.backend.dto.EnrollmentDto;

import java.util.List;

public interface EnrollmentService {
    EnrollmentDto enroll(EnrollmentDto enrollmentDto) throws Exception;
    List<EnrollmentDto> getEnrollmentsByJobSeeker(Long jobSeekerId);
}
