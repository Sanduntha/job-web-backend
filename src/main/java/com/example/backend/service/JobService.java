package com.example.backend.service;

import com.example.backend.dto.JobDto;

import java.util.List;

public interface JobService {
    JobDto addJob(JobDto jobDto) throws Exception;
    JobDto updateJob(JobDto jobDto) throws Exception;
    void deleteJob(Long jobId) throws Exception;
    List<JobDto> getJobsByEmployer(Long employerId);
    List<JobDto> searchJobs(String keyword);
}
