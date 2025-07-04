package com.example.backend.service.Impl;

import com.example.backend.dto.JobDto;
import com.example.backend.entity.EmployerProfile;
import com.example.backend.entity.Job;
import com.example.backend.repo.EmployerProfileRepo;
import com.example.backend.repo.JobRepo;
import com.example.backend.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepo jobRepo;
    private final EmployerProfileRepo employerProfileRepo;

    @Override
    public JobDto addJob(JobDto jobDto) {
        // Ensure employer exists before saving job
        EmployerProfile employer = employerProfileRepo.findById(jobDto.getEmployerId())
                .orElseThrow(() -> new RuntimeException("Employer not found"));

        Job job = new Job();
        job.setTitle(jobDto.getTitle());
        job.setDescription(jobDto.getDescription());
        job.setCompanyName(jobDto.getCompanyName());
        job.setLocation(jobDto.getLocation());
        job.setEmployer(employer); // ✅ set entity, not ID

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

    private JobDto toDto(Job job) {
        return new JobDto(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getCompanyName(),
                job.getLocation(),
                job.getEmployer() != null ? job.getEmployer().getId() : null,
                job.getApplications()
        );
    }

}
