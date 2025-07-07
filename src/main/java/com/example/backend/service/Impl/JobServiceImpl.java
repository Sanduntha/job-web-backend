package com.example.backend.service.Impl;

import com.example.backend.dto.JobDto;
import com.example.backend.entity.EmployerProfile;
import com.example.backend.entity.Job;
import com.example.backend.repo.EmployerProfileRepo;
import com.example.backend.repo.JobRepo;
import com.example.backend.service.JobService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepo jobRepo;
    private final EmployerProfileRepo employerProfileRepo;
    private final ModelMapper modelMapper;

    @Override
    public JobDto addJob(JobDto dto) throws Exception {
        EmployerProfile employer = employerProfileRepo.findById(dto.getEmployerId())
                .orElseThrow(() -> new Exception("Employer not found"));

        Job job = new Job();
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setEmployer(employer);
        job.setCompanyName(employer.getCompanyName());

        Job saved = jobRepo.save(job);

        return toDto(saved);
    }

    @Override
    public JobDto updateJob(JobDto jobDto) {
        Job job = jobRepo.findById(jobDto.getId())
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setTitle(jobDto.getTitle());
        job.setDescription(jobDto.getDescription());
        job.setCompanyName(jobDto.getCompanyName());
        job.setLocation(jobDto.getLocation());

        Job updated = jobRepo.save(job);
        return toDto(updated);
    }

    @Override
    public void deleteJob(Long jobId) {
        jobRepo.deleteById(jobId);
    }

    @Override
    public List<JobDto> getJobsByEmployer(Long employerId) {
        return jobRepo.findByEmployerId(employerId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobDto> searchJobs(String keyword) {
        return jobRepo.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobDto> getAllJobs() {
        return jobRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ✅ Safe mapping method
    private JobDto toDto(Job job) {
        JobDto dto = new JobDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setCompanyName(job.getCompanyName());
        dto.setLocation(job.getLocation());
        dto.setEmployerId(job.getEmployer() != null ? job.getEmployer().getId() : null);
        return dto;
    }
}
