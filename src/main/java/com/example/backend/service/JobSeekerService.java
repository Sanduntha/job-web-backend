    package com.example.backend.service;
    
    import com.example.backend.dto.JobSeekerDto;
    
    public interface JobSeekerService {
        JobSeekerDto createJobSeeker(JobSeekerDto jobSeekerDto) throws Exception;
        JobSeekerDto getByUserId(Long userId) throws Exception;
    }
